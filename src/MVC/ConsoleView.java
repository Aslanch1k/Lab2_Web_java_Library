package MVC;

import org.w3c.dom.Entity;
import java.util.List;

public class ConsoleView implements View {

    @Override
    public void showEntity(Entity entity) {
        System.out.println(entity.toString());
    }

    @Override
    public void showEntities(List<Entity> entities) {
        String rez = "";
        for (Entity entity : entities){
            rez += entity.toString() + "\n";
        }
        System.out.println(rez);
    }
}
