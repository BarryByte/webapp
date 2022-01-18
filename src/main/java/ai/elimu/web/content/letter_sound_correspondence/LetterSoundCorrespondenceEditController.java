package ai.elimu.web.content.letter_sound_correspondence;

import java.util.List;
import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundCorrespondenceContributionEventDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSoundCorrespondence;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ai.elimu.dao.LetterSoundCorrespondenceDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import ai.elimu.web.context.EnvironmentContextLoaderListener;

@Controller
@RequestMapping("/content/letter-sound-correspondence/edit")
public class LetterSoundCorrespondenceEditController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterSoundCorrespondenceDao letterSoundCorrespondenceDao;
    
    @Autowired
    private LetterSoundCorrespondenceContributionEventDao letterSoundCorrespondenceContributionEventDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        LetterSoundCorrespondence letterSoundCorrespondence = letterSoundCorrespondenceDao.read(id);
        model.addAttribute("letterSoundCorrespondence", letterSoundCorrespondence);
        
        model.addAttribute("timeStart", System.currentTimeMillis());
        
        List<Letter> letters = letterDao.readAllOrdered();
        model.addAttribute("letters", letters);
        
        List<Allophone> allophones = allophoneDao.readAllOrdered();
        model.addAttribute("allophones", allophones);
        
        model.addAttribute("letterSoundCorrespondenceContributionEvents", letterSoundCorrespondenceContributionEventDao.readAll(letterSoundCorrespondence));
        
        List<Word> words = wordDao.readAllOrderedByUsage();
        model.addAttribute("words", words);
        
        return "content/letter-sound-correspondence/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            @Valid LetterSoundCorrespondence letterSoundCorrespondence,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        // Check if the LetterSoundCorrespondence already exists
        LetterSoundCorrespondence existingLetterSoundCorrespondence = letterSoundCorrespondenceDao.read(letterSoundCorrespondence.getLetters(), letterSoundCorrespondence.getAllophones());
        if ((existingLetterSoundCorrespondence != null) && !existingLetterSoundCorrespondence.getId().equals(letterSoundCorrespondence.getId())) {
            result.rejectValue("letters", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("letterSoundCorrespondence", letterSoundCorrespondence);
            
            model.addAttribute("timeStart", System.currentTimeMillis());
            
            List<Letter> letters = letterDao.readAllOrdered();
            model.addAttribute("letters", letters);
            
            List<Allophone> allophones = allophoneDao.readAllOrdered();
            model.addAttribute("allophones", allophones);
            
            model.addAttribute("letterSoundCorrespondenceContributionEvents", letterSoundCorrespondenceContributionEventDao.readAll(letterSoundCorrespondence));
            
            return "content/letter-sound-correspondence/edit";
        } else {
            letterSoundCorrespondence.setTimeLastUpdate(Calendar.getInstance());
            letterSoundCorrespondence.setRevisionNumber(letterSoundCorrespondence.getRevisionNumber() + 1);
            letterSoundCorrespondenceDao.update(letterSoundCorrespondence);
            
            LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent = new LetterSoundCorrespondenceContributionEvent();
            letterSoundCorrespondenceContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            letterSoundCorrespondenceContributionEvent.setTime(Calendar.getInstance());
            letterSoundCorrespondenceContributionEvent.setLetterSoundCorrespondence(letterSoundCorrespondence);
            letterSoundCorrespondenceContributionEvent.setRevisionNumber(letterSoundCorrespondence.getRevisionNumber());
            letterSoundCorrespondenceContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            letterSoundCorrespondenceContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            letterSoundCorrespondenceContributionEventDao.create(letterSoundCorrespondenceContributionEvent);
            
            String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/letter-sound-correspondence/edit/" + letterSoundCorrespondence.getId();
            DiscordHelper.postChatMessage(
                    "Letter-sound correspondence edited: " + contentUrl,
                    "\"" + letterSoundCorrespondenceContributionEvent.getLetterSoundCorrespondence() + "\"",
                    "Comment: \"" + letterSoundCorrespondenceContributionEvent.getComment() + "\"",
                    null
            );
            
            return "redirect:/content/letter-sound-correspondence/list#" + letterSoundCorrespondence.getId();
        }
    }
}
