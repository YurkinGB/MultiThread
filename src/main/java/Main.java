public class Main {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;
    static float[] arr = new float[SIZE];

    public static void main(String[] args) {

        for (float x : arr ) {
            x = 1;
        }

        method1();
        method2();
    }

    public static void method1(){
        long a = System.currentTimeMillis();

        for (int i = 0; i < SIZE; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println("Method 1->" + ((System.currentTimeMillis() - a) / 1000) + "sec");
    }

    public  static void method2(){

        long a = System.currentTimeMillis();

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                float[] a1 = new float[HALF];
                System.arraycopy(arr, 0, a1, 0, HALF);
                for (int i = 0; i < HALF; i++){
                    a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }

                System.arraycopy(a1, 0, arr, 0, HALF);
            }

        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                float[] a2 = new float[HALF];
                System.arraycopy(arr, HALF, a2, 0, HALF);
                for (int i = 0; i < HALF; i++){
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }

                System.arraycopy(a2, 0, arr, HALF, HALF);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Method 2->" + ((System.currentTimeMillis() - a) / 1000) + "sec");

    }

}
