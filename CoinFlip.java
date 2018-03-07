import java.util.Random;

class CoinFlip implements Runnable
{
  private Random rand;
  private int thread_id;
  private int num_flips;

  private static Integer[] thread_heads;

  public CoinFlip(int id, int flips)
  {
    rand = new Random();
    thread_id = id;
    num_flips = flips;
  }

  public void run()
  {
    thread_heads[thread_id] = 0;
    for (int i = 0; i < num_flips; i++)
    {
      if (rand.nextBoolean())
      {
        thread_heads[thread_id]++;
      }
    } 
  }

  public static void main (String[] args)
  {
    if (2 != args.length)
    {
      System.out.println("Usage: java CoinFlip #threads #flips");
      return;
    }

    int num_threads = Integer.parseInt(args[0]);

    int total_flips = Integer.parseInt(args[1]);

    Thread[] threads = new Thread[num_threads];
    thread_heads = new Integer[num_threads];

    long time = System.currentTimeMillis();

    for ( int i=0; i<num_threads; i++ )
    {
      threads[i] = new Thread ( new CoinFlip(i, total_flips/num_threads) );
      threads[i].start();
    }

    int heads = 0;

    for ( int i=0; i<num_threads; i++ )
    {
      try
      {
        threads[i].join();
        heads += thread_heads[i];
      }
      catch (InterruptedException e)
      {
         System.out.println("Thread interrupted.  Exception: " + e.toString() +
                           " Message: " + e.getMessage()) ;
        return;
      }
    }
    System.out.printf("%d heads in %s coin tosses\n", heads, args[1]);
    System.out.printf("Elapsed time: %dms\n", System.currentTimeMillis() - time);
  }
}
