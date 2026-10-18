package pl.pollub.cleancode.common;


import lombok.RequiredArgsConstructor;

//singleton do zaimplementowania

//moze cos na ile zrobmy że:
// ten singleton przechowuje historie obecnych requestow oraz
//ktos wywoluje kontroler - w tym momencie zapisujemy tutaj w liscie czy mapie ze wywolal z taką i taką trescia
// i w pamieci mamy historie wywolan aplikacji

public class SampleSingleton {


    private SampleSingleton(){

    }
}
