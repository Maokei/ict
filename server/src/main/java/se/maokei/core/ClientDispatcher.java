package se.maokei.core;

import se.maokei.connection.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

/**
 * ClientDispatcher
 * <p>The interrupt variable will control the thread. If
 * false, the thread will run.</p>
 * <p>Semaphore will control the Queue. Thread waits for Semaphore until there are clients in Queue.</p>
 * */
public class ClientDispatcher extends Thread implements IClientDispatcher {
  private static final int SLEEP_TIME = 5000;
  private boolean interrupt = false;
  private final Semaphore semaphore = new Semaphore(0);
  private final Queue<Client> waitingQueue = new ConcurrentLinkedQueue<>();
  private final Collection<Client> clientsToRemove = new ArrayList<>();
  private final int waitingQueueSize;

  public ClientDispatcher(int waitingQueueSize) {
    this.waitingQueueSize = waitingQueueSize;
  }
  @Override
  public boolean hasClientInQueue() {
    return !waitingQueue.isEmpty();
  }

  @Override
  public Client getClientFromQueue() {
    return waitingQueue.poll();
  }

  /**
   * <p>First checks if queue can handle more clients before adding another one, if so Semaphore is released.</p>
   * */
  @Override
  public boolean addClientToQueue(Client client) {
    if (waitingQueue.size() < waitingQueueSize) {
      waitingQueue.add(client);
      semaphore.release();
      return true;
    }
    return false;
  }

  @Override
  public void run() {
    while(!interrupt) { // infinite loop depending on semaphore
      while(waitingQueue.isEmpty() && !interrupt) { // Another loop which will depend on the semaphore
        try {
          /*If we were waiting for the semaphore using if,
          then the thread would start to execute its logic unnecessarily. Therefore, it's
          important to wait for the semaphore in a loop.*/
          /*
          If the thread wakes up at a semaphore and the queue is empty, or the
          interupt flag is false, the thread will be put to
          sleep again. It's important to have the interupt variable.
          Otherwise, we wouldn't be able to terminate the thread while shutting down the
          server.
          */
          semaphore.acquire();
        } catch (InterruptedException ignored) {}
      }

      if (interrupt) {
        clientsToRemove.addAll(waitingQueue);
      } else {
        final int count = waitingQueue.size();
        waitingQueue.forEach(client -> {
          try {
            //client.writer.write(("count: " + count + "\n").getBytes());
            //client.writer.flush();
            client.getWriter().write(("count: " + count + "\n").getBytes());
            client.getWriter().flush();
          } catch (IOException e) {
            clientsToRemove.add(client);
          }
        });
      }

      waitingQueue.removeAll(clientsToRemove);
      for (Client client : clientsToRemove) {
        client.close();
      }
      clientsToRemove.clear();

      try {
        Thread.sleep(SLEEP_TIME);
      } catch (InterruptedException ignored) {}
    }
  }

  /**
   * shutdown
   * <p>In this method we do three things:</p>
   * <ol>
   *   <li>set the interupt variable to true</li>
   *   <li>release the semaphore</li>
   *   <li>wait for the thread to terminate</li>
   * </ol>
   * */
  @Override
  public void shutdown() {
    interrupt = true;
    semaphore.release();
    try {
      join();
    } catch (InterruptedException ignored) { }

  }
}
