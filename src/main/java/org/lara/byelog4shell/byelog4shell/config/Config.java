package org.lara.byelog4shell.byelog4shell.config;

public enum Config {
  KICK_MESSAGE("kick_message", "Exploit Abuse"),
  INGAME_STATE("ingame_state", true),
  INGAME_MESSAGE("ingame_message", "%player% tried to use Log4Shell Exploit with content %content%"),
  DISCORD_STATE("discord_state", true),
  DISCORD_MESSAGE("discord_message", "%player% tried to use Log4Shell Exploit with content `%content%`"),
  DISCORD_LINK("discord_link", "");

  private final String key;
  private final Object value;

  Config(String key, Object value) {
    this.key = key;
    this.value = value;
  }
  public Object getValue() {
    return value;
  }

  public String getKey() {
    return key;
  }
}
