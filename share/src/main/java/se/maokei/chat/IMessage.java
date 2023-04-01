package se.maokei.chat;

import java.io.Serializable;

public interface IMessage extends Serializable {
  String getType();
  Object getData();

  default boolean isSuccess() {
    return true;
  }
}