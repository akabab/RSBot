import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.*;
import java.util.Arrays;

@Script.Manifest(
        name = "BlueDye",
        description = "Make blue dyes from Aggie..",
        properties = "client=6"
)

public class BlueDye extends PollingScript<ClientContext> {
    final int npc_AggieId = 922;
    final int item_BlueDyeId = 1767;
    final int obj_doorId = 1239; // Position(3088, 3259) in front of door (outside) -> make sure it is opened (id = 1240)

    final private int[] bankerIds = {4456, 4457, 4458, 4459};

    public Npc aggie;
    public boolean isInAggieHouse = false;

    @Override
    public void start() {
        System.out.println("Script started..");
    }

    @Override
    public void poll() {

        Random rn = new Random();
        ctx.movement.findPath(new Tile(rn.nextInt(3092, 3244), rn.nextInt(3088, 3259))).traverse();

        GameObject door = ctx.objects.select().id(obj_doorId).nearest().poll();
        RelativeLocation doorPos = door.relative();
//        ptr("DoorPos: " + doorPos.x() + ", " + doorPos.z());
//        if (!isInventoryFull()) {
//            if (isInAggieHouse)
//                makeDye();
//            else
//                reachAggie();
//        }
//        else
//            bank();
    }

    public void reachAggie() {
        ptr("Reach Aggie");
        aggie = ctx.npcs.select().id(npc_AggieId).poll();
        ptr("Moving to aggie");
        moveTo(aggie);
        isInAggieHouse = true;
    }

    public void makeDye() {
        ptr("Make Dye");
//        aggie = ctx.npcs.select().id(npc_AggieId).poll();
        ctx.camera.turnTo(aggie);
        aggie.interact("Make-dyes");
        clickComponent(ctx.widgets.widget(1188).component(18));
        clickComponent(ctx.widgets.widget(1191).component(7));
    }

    public void moveTo(Npc npc) {
        while (!ctx.players.local().inMotion())
            ctx.movement.step(npc);
        while (ctx.players.local().inMotion())
            sleep(100);
        ctx.camera.turnTo(npc);
    }

    public void clickComponent(Component c) {
        //need timeout
        int attempt = 0;
        while (!c.valid()) {
            sleep(100);
            if (attempt >= 10)
                return;
            attempt++;
            ptr("waiting for component");
        }
        ptr("clicking on component: " + c.text());
        c.click();
    }

    public boolean isInventoryFull() {
        return ctx.backpack.select().count() == 28;
    }
    
    public void bank() {
        isInAggieHouse = false;

        Npc banker = ctx.npcs.select().id(bankerIds).nearest().poll();
        ptr("Move to bank");
        moveTo(banker);
        ptr("Talk to banker");
        banker.interact("Bank");
        ptr("wait opening bank");
        while (!ctx.bank.opened())
            sleep(250);
        ptr("Bank depositing");
        ctx.bank.deposit(item_BlueDyeId, Bank.Amount.ALL);
        ptr("Close bank");
        ctx.bank.close();
    }

    public void ptr(String msg) {
        System.out.println(msg);
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(randomInt(ms, (int)(ms * 1.5)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int randomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(min, max);
    }
}