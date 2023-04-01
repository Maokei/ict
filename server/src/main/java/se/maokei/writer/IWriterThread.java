package se.maokei.writer;

import se.maokei.chat.IMessage;
import se.maokei.core.IThreadControl;

import java.io.ObjectOutputStream;

public interface IWriterThread extends IThreadControl {
  void sendMessage(ObjectOutputStream writer, IMessage message);
}
