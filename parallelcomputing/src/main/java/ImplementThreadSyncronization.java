import java.util.List;

public class ImplementThreadSyncronization {

    /*
    20.3
    */

    public static class OddEvenMonitor {
        public static final boolean ODD_TURN = true;
        public static final boolean EVEN_TURN = false;
        private boolean turn = EVEN_TURN;
        public synchronized void waitTurn(boolean oldTurn) {
            while (turn != oldTurn) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // our turn, move on
        }

        public synchronized void toggleTurn() {
            turn = !turn;
            notify();
        }
    }

    public static class OddThread extends Thread {

        private List<Integer> list;

        private OddEvenMonitor oddEvenMonitor;

        public OddThread(List<Integer> list) {
            this.list = list;
        }

        public void setOddEvenMonitor(OddEvenMonitor oddEvenMonitor) {
            this.oddEvenMonitor = oddEvenMonitor;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 99; i += 2) {
                oddEvenMonitor.waitTurn(OddEvenMonitor.ODD_TURN);
                list.add(i);
                oddEvenMonitor.toggleTurn();
            }
        }

    }

    public static class EvenThread extends Thread {

        private List<Integer> list;

        private OddEvenMonitor oddEvenMonitor;

        public EvenThread(List<Integer> list) {
            this.list = list;
        }

        public void setOddEvenMonitor(OddEvenMonitor oddEvenMonitor) {
            this.oddEvenMonitor = oddEvenMonitor;
        }

        @Override
        public void run() {
            for (int i = 0; i <= 98; i += 2) {
                oddEvenMonitor.waitTurn(OddEvenMonitor.EVEN_TURN);
                list.add(i);
                oddEvenMonitor.toggleTurn();
            }
        }
    }
}
