import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.*;
import java.util.Random;

@Script.Manifest(
        name = "BlueDye",
        description = "Make blue dyes from Aggie..",
        properties = "client=6"
)

public class BlueDye extends PollingScript<ClientContext> {
    final int npc_AggieId = 922;
    final int item_BlueDyeId = 1767;
    final int obj_AggieDoorId = 1239; // Position(3088, 3259) in front of door (outside) -> make sure it is opened (id = 1240)

    final private int[] bankerIds = {4456, 4457, 4458, 4459};

    public Npc aggie;
    public boolean isInAggieHouse = false;

    @Override
    public void start() {
        System.out.println("Script started..");
    }

    @Override
    public void poll() {
        if (!isInventoryFull()) {
            if (isInAggieHouse)
                makeDye();
            else
                reachAggie();
        }
        else
            bank();
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
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
//    public void walkToBanker(){
//        System.out.println("Walking to banker");
//        Npc npc = ctx.npcs.select().id(bankerIds).nearest().poll();
//        ctx.movement.step(npc);
//        ctx.camera.turnTo(npc);
//        sleep(150, 250);
//        while(ctx.players.local().inMotion()==false){
//            ctx.movement.step(npc);
//            ctx.camera.turnTo(npc);
//        }
//        sleep(150, 250);
//        while(ctx.players.local().inMotion()) {
//            sleep(150, 250);
//        }
//        interactBanker(npc);
//    }
//
//    public void walkBack(){
//        System.out.println("Walking back because tree");
//        Npc npc = ctx.npcs.select().id(bankerIds).nearest().poll();
//        ctx.movement.step(npc);
//        ctx.camera.turnTo(npc);
//        sleep(150, 250);
//        while(ctx.players.local().inMotion()) {
//            sleep(150, 250);
//        }
//        startChop();
//    }
//
//    public void interactBanker(Npc banker){
//        System.out.println("talkin' to banker");
//        ctx.camera.turnTo(banker);
//        sleep(250, 350);
//        banker.interact("Bank");
//        sleep(250, 350);
//        depositVials();
//    }
//
//    public void walkToMerch(String action){
//        System.out.println("Walking to merch");
//        Npc npc = ctx.npcs.select().id(clerckIds).nearest().poll();
//        ctx.movement.step(npc);
//        ctx.camera.turnTo(npc);
//        sleep(150, 250);
//        while(ctx.players.local().inMotion()) {
//            sleep(150, 250);
//        }
//        interactMerch(npc,action);
//    }
//
//    public void interactMerch(Npc merch,String theAction){
//        System.out.println("talkin' to merch");
//        ctx.camera.turnTo(merch);
//        sleep(250, 350);
//        merch.interact("Exchange");
//        sleep(1250, 1350);
//        if(theAction.equals("sell")){
//            sellVials();
//        }
//        else{
//            amtMoney=ctx.backpack.moneyPouchCount();
//            buyVials();
//        }
//    }
//    // public void
//
//    public void depositVials(){
//        j=0;
//        System.out.println("depositin'");
//        while(ctx.bank.opened()==false){
//            if(j>7){
//                walkToBanker();
//            }
//            sleep(350, 650);
//            j++;
//        }
//        ctx.bank.depositInventory();
//        sleep(750, 1050);
//        withdrawVials();
//    }
//
//    public void withdrawVials(){
//        System.out.println("Withdrawin'");
//        while(ctx.bank.opened()==false){
//            sleep(350, 650);
//        }
//        ctx.bank.depositInventory();
//        sleep(650, 1050);
//        if(ctx.bank.indexOf(EmptyVialID) != -1){
//            ctx.bank.withdrawMode(false);
//            sleep(750, 1050);
//            ctx.bank.withdraw(EmptyVialID, Bank.Amount.ALL);
//            sleep(750, 1050);
//            ctx.bank.close();
//            fillVials(false);
//        }else{
//            ctx.bank.withdrawMode(true);
//            sleep(750, 1050);
//            ctx.bank.withdraw(FullID, Bank.Amount.ALL);
//            sleep(750, 1050);
//            ctx.bank.close();
//            walkToMerch("sell");
//        }
//    }
//
//    public void buyVials(){
//        System.out.println("buyin'");
//        sleep(2850, 3200);
//        int buyAmt = (amtMoney/5)-1;
//        Component buy = ctx.widgets.component(105, 19);
//        buy.click(true);
//        sleep(1850, 2200);
//        ctx.keyboard.send("Vial");
//        sleep(3850, 4200);
//        //105,142 = -5%;
//        Component buy2 = ctx.widgets.component(389, 4).component(1);
//        buy2.click(true);
//        sleep(1850, 2200);
//        Component buy3 = ctx.widgets.component(105, 58);
//        buy3.click(true);
//        sleep(1850, 2200);
//        Component buy4 = ctx.widgets.component(105, 58);
//        buy4.click(true);
//        sleep(2850, 4200);
//        ctx.keyboard.send("" + buyAmt);
//        sleep(550, 800);
//        ctx.keyboard.send("{VK_ENTER down}");
//        sleep(250, 300);
//        ctx.keyboard.send("{VK_ENTER up}");
//        sleep(1850, 2200);
//        Component buy5 = ctx.widgets.component(105, 60);
//        buy5.click(true);
//        sleep(550, 800);
//        buy5.click(true);
//        sleep(550, 800);
//        buy5.click(true);
//        sleep(550, 800);
//        buy5.click(true);
//        sleep(550, 800);
//        Component buy6 = ctx.widgets.component(105, 65);
//        buy6.click(true);
//        sleep(20050, 22000);
//        Component buy7 = ctx.widgets.component(105, 18);
//        buy7.click(true);
//        sleep(2050, 2200);
//        Component sell7 = ctx.widgets.component(105, 76);
//        sell7.click(true);
//        sleep(1050, 1200);
//        Component sell9 = ctx.widgets.component(105, 78);
//        sell9.click(true);
//        sleep(1050, 1200);
//        Component sell8 = ctx.widgets.component(105, 87).component(1);
//        sell8.click(true);
//        sleep(1050, 1200);
//        Item a = ctx.backpack.itemAt(0);
//        if((a.id()==EmptyVialID)||(a.id()==NotedEmptyID)){
//            walkToBanker();
//        }
//        else{
//            startChop();
//        }
//
//    }
//    public void startChop(){
//        System.out.println("chopin'");
//        //get an inventory then sell it then buy vials
//        while(ctx.backpack.select().count()<28) {
//            GameObject tree = ctx.objects.select().id(treeIds).nearest().poll();
//            ctx.movement.step(tree);
//            ctx.camera.turnTo(tree);
//            if(ctx.movement.destination().y()<3465||ctx.movement.destination().y()>3517){
//                walkBack();
//            }
//            else {
//                sleep(150, 250);
//                while(ctx.players.local().inMotion()) {
//                    sleep(150, 250);
//                }
//                if (tree.inViewport()) {
//                    tree.interact("Chop");
//                }
//                sleep(1550, 2200);
//                while (ctx.players.local().animation() != -1) {
//                    sleep(350, 500);
//                }
//            }
//        }
//        depositVials();
//
//    }
//    public void sellVials(){
//        System.out.println("sellin'");
//        sleep(2850, 3200);
//        Component sell = ctx.widgets.component(105, 183);
//        sell.click(true);
//        sleep(850, 1200);
//        Component sell1 = ctx.widgets.component(107, 3).component(0);
//        sell1.click(true);
//        sleep(850, 1200);
//        Component sell2 = ctx.widgets.component(105, 57);
//        sell2.click(true);
//        sleep(850, 1200);
//        Component sell3 = ctx.widgets.component(105, 61);
//        sell3.click(true);
//        sleep(300, 500);
//        sell3.click(true);
//        sleep(300, 500);
//        sell3.click(true);
//        sleep(300, 500);
//        sell3.click(true);
//        sleep(1050, 1200);
//        Component sell4 = ctx.widgets.component(105, 65);
//        sell4.click(true);
//        sleep(10050, 15000);
//        Component sell5 = ctx.widgets.component(105, 30);
//        sell5.click(true);
//        sleep(20050, 22000);
//        Component sell6 = ctx.widgets.component(105, 78);
//        sell6.click(true);
//        sleep(1050, 1200);
//        Component sell7 = ctx.widgets.component(105, 76);
//        sell7.click(true);
//        sleep(1050, 1200);
//        Component sell8 = ctx.widgets.component(105, 87).component(1);
//        sell8.click(true);
//        sleep(1050, 1200);
//        Item a = ctx.backpack.itemAt(0);
//        if((a.id()==FullID)||(a.id()==NotedFullID)){
//            walkToMerch("sell");
//        }
//        else{
//            walkToMerch("buy");
//        }
//    }
//
//    public void fillVials(boolean skipWait){
//        System.out.println("fillin', do we need to wait: "+!skipWait);
//        ctx.movement.step(fountain);
//        ctx.camera.turnTo(fountain);
//        if(skipWait==false) {
//            sleep(150, 250);
//            while(ctx.players.local().inMotion()) {
//                sleep(150, 250);
//            }
//        }
//        else{
//            sleep(250, 350);
//        }
//        ctx.backpack.itemAt(0).interact("Use");
//        sleep(850, 1200);
//        fountain.interact("Use");
//        while(ctx.players.local().inMotion()) {
//            sleep(150, 250);
//        }
//        sleep(2850, 3200);
//        Component Fill = ctx.widgets.component(1370, 38);
//        Fill.click(true);
//        sleep(17000, 18000);
//        Item a = ctx.backpack.itemAt(0);
//        if(a.id()==EmptyVialID){
//            fillVials(true);
//        }else {
//            walkToBanker();
//        }
//    }
//
//    public void sleep(int ms) {
//        try {
//            Thread.sleep(ms);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void sleep(int ms1, int ms2) {
//        int dif=ms2-ms1;
//        sleep((int)((Math.random()*dif)+ms1));
//    }
}