package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.EmojiDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;

public class EmojiDaoJpa extends GenericDaoJpa<Emoji> implements EmojiDao {

    @Override
    public Emoji readByGlyph(String glyph, Language language) throws DataAccessException {
        try {
            return (Emoji) em.createQuery(
                "SELECT e " +
                "FROM Emoji e " +
                "WHERE e.glyph = :glyph " +
                "AND e.language = :language")
                .setParameter("glyph", glyph)
                .setParameter("language", language)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Emoji '" + glyph + "' was not found");
            return null;
        }
    }

    @Override
    public List<Emoji> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT e " +
            "FROM Emoji e " +
            "WHERE e.language = :language " +
            "ORDER BY e.glyph")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public List<Emoji> readAllLabeled(Word word, Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT e " +
            "FROM Emoji e " +
            "WHERE :word MEMBER OF e.words " + 
            "AND e.language = :language " +
            "ORDER BY e.glyph")
            .setParameter("word", word)
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public Long readCount(Language language) throws DataAccessException {
        return (Long) em.createQuery(
            "SELECT COUNT(e) " +
            "FROM Emoji e " +
            "WHERE e.language = :language")
            .setParameter("language", language)
            .getSingleResult();
    }
}
