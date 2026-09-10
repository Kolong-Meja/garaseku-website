package com.faisalrmdhn.GaraseKu.util;

import java.security.SecureRandom;
import java.time.Clock;

import org.springframework.stereotype.Component;

@Component
public class UlidGenerator implements IdGenerator {

  private static final char[] CROCKFORD_BASE32 = "0123456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray();
  private static final int TIMESTAMP_CHARS = 10;
  private static final int RANDOMNESS_BYTES = 10;
  private static final int ULID_LENGTH = 26;
  private final Clock clock;
  private final SecureRandom random = new SecureRandom();

  public UlidGenerator() {
    this(Clock.systemUTC());
  }

  UlidGenerator(Clock clock) {
    this.clock = clock;
  }

  @Override
  public String generate() {
    byte[] randomness = new byte[RANDOMNESS_BYTES];
    random.nextBytes(randomness);
    return encode(clock.millis(), randomness);
  }

  private String encode(long timestamp, byte[] randomness) {
    char[] chars = new char[ULID_LENGTH];
    for (int i = TIMESTAMP_CHARS - 1; i >= 0; i--) {
      chars[i] = CROCKFORD_BASE32[(int) (timestamp & 0x1F)];
      timestamp >>>= 5;
    }
    int buffer = 0;
    int bitsInBuffer = 0;
    int cursor = TIMESTAMP_CHARS;
    for (byte b : randomness) {
      buffer = (buffer << 8) | (b & 0xFF);
      bitsInBuffer += 8;
      while (bitsInBuffer >= 5) {
        bitsInBuffer -= 5;
        chars[cursor++] = CROCKFORD_BASE32[(buffer >>> bitsInBuffer) & 0x1F];
      }
    }
    return new String(chars);
  }
}
