package MVC;

import org.w3c.dom.Entity;
import java.util.List;

/**
 * Interface view, part of MVC pattern
 */
public interface View {
    public void showEntity(Entity entity);
    public void showEntities(List<Entity> entities);
}
