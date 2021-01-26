package service;



public class Main {

    public static void main(String[] args) {

        try {
            BowlingScoreEngine engine = new BowlingScoreEngine();
            if(args != null && args.length > 0)
                engine.processScoreGame(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

}
