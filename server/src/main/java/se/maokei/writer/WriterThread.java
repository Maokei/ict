package se.maokei.writer;

import com.google.inject.Inject;
import se.maokei.chat.IMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class WriterThread extends Thread implements IWriterThread {
  private final Semaphore semaphore = new Semaphore(0);
  private final Queue<QueueTuple> messageQueue = new ConcurrentLinkedQueue<>();
  private boolean working = false;
  private boolean interrupt = false;

  public WriterThread() {
    super("WriterThread");
  }

  /**
   * sendMessage
   * <p>
   * Sending a message will be nothing but adding a message to the message
   * queue, and if the thread isn't working, we'll wake it up and set the
   * working variable to true:
   * </p>
   * */
  public void sendMessage(ObjectOutputStream outputStream, IMessage message) {
    messageQueue.add(new QueueTuple(outputStream, message));
    if (!working) {
      working = true;
      semaphore.release();
    }
  }

  /**
   * shutdown
   * <p>We set the interrupt variable to true, release the
   * semaphore, and wait for the writer thread to end.</p>
   * */
  @Override
  public void shutdown() {
    interrupt = true;
    semaphore.release();
    try {
      join();
    } catch (InterruptedException ignored) {}
  }

  @Override
  public void run() {
    while(!interrupt) {
      while(messageQueue.isEmpty() && !interrupt) {
        try {
          semaphore.acquire();
        } catch (InterruptedException ignored) {}
      }
      working = true;
      while(!messageQueue.isEmpty()) {
        final QueueTuple entry = messageQueue.poll();
        try {
          //TODO entry.writer.writeLine(entry.message);
          entry.writer.writeUTF((String) entry.message);
          entry.writer.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      working = false;
    }
  }

  private static final class QueueTuple {
    final Object message;
    final ObjectOutputStream writer;

    private QueueTuple(ObjectOutputStream writer, Object message) {
      this.message = message;
      this.writer = writer;
    }
  }
}
