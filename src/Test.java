import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.*;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.ClientContext;

import java.util.Arrays;

@Script.Manifest(
        name = "Test",
        description = "testing..",
        properties = "client=6"
)

public class Test extends PollingScript<ClientContext> {
    final int npc_AggieId = 922;
    final int item_BlueDyeId = 1767;
    String[] aggieActions;

    //right-click -> make-dyes (2) -> 2 -> space -> loop
//
//    final private int EmptyVialID=229;
//    final private int NotedFullID=228;
//    final private int FullID=227;
//    final private int NotedEmptyID=230;
//    private int i=0;
//    private int j=0;
//    private int amtMoney=0;
//    private GameObject fountain;
//    final private int[]bankerIds={2718,3418,3293,3416};
//    final private int[]clerckIds={2240,2241,2593,1419};
//    final private int[]treeIds={93384,38787,38785,93385,38783};
    @Override
    public void start() {
        System.out.println("Script started..");
    }

    public boolean once = true;

    @Override
    public void poll() {

        if (once) {
//            Item[] items = ctx.backpack.items();

            Npc aggie = ctx.npcs.select().id(npc_AggieId).poll();
            ctx.movement.step(aggie);
            ctx.camera.turnTo(aggie);
            aggieActions = aggie.actions();
            aggie.interact("Make-dyes");
            Condition.sleep(2000);
            Component c = ctx.widgets.widget(1188).component(18);
            Component c2 = ctx.widgets.widget(1191).component(7);
            if (c.valid())
                c.click();
            Condition.sleep(1000);
            if (c2.valid())
                c2.click();
            once = false;
        }
//
//       if(i==0){
//            i++;
//            fountain=ctx.objects.select().id(47150).nearest().poll();
//            walkToBanker();
//        }
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