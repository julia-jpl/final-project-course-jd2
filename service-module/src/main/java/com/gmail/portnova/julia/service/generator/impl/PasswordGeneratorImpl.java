package com.gmail.portnova.julia.service.generator.impl;

import com.gmail.portnova.julia.service.generator.PasswordGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.Collectors;

import static com.gmail.portnova.julia.service.constant.PasswordGenerationConstant.*;

@Component
public class PasswordGeneratorImpl implements PasswordGenerator {
    @Override
    public String generatePassword(int length, int startAsciiCod, int lastAsciiCod) {
        return new Random().ints(length, startAsciiCod, lastAsciiCod)
                .mapToObj(characterCod -> String.valueOf((char)characterCod))
                .collect(Collectors.joining());
    }
}
