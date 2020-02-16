package ai.elimu.web.content.emoji;

import java.util.Calendar;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.EmojiDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/emoji/create")
public class EmojiCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private EmojiDao emojiDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Emoji emoji = new Emoji();
        model.addAttribute("emoji", emoji);

        return "content/emoji/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @Valid Emoji emoji,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        Emoji existingEmoji = emojiDao.readByGlyph(emoji.getGlyph(), language);
        if (existingEmoji != null) {
            result.rejectValue("glyph", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("emoji", emoji);
            
            return "content/emoji/create";
        } else {
            emoji.setTimeLastUpdate(Calendar.getInstance());
            emojiDao.create(emoji);
            
            return "redirect:/content/emoji/list#" + emoji.getId();
        }
    }
}
