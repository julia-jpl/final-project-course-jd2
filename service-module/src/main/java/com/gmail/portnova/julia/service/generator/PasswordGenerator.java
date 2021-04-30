package com.gmail.portnova.julia.service.generator;

public interface PasswordGenerator {
    String generatePassword(int length, int startAsciiCod, int lastAsciiCod);
}
