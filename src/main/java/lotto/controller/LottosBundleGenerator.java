package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.LottoCount;
import lotto.domain.LottoPrice;

import java.util.List;

public class LottosBundleGenerator implements LottoGenerator {
    private final List<LottoGenerator> lottosGenerators;

    public LottosBundleGenerator(LottoPrice price, List<String> manualLottoText) {
        this(toLottosGenerators(price, manualLottoText));
    }

    public LottosBundleGenerator(List<LottoGenerator> lottosGenerators) {
        this.lottosGenerators = lottosGenerators;
    }

    private static List<LottoGenerator> toLottosGenerators(LottoPrice price, List<String> manualLottoText) {
        return List.of(new AutoLottoMachine(new LottoCount(price.count() - manualLottoText.size())), new ManualLottoMachine(manualLottoText));
    }

    @Override
    public Lotto generate() {
        Lotto lotto = new Lotto();

        for (LottoGenerator lottosGenerator : lottosGenerators) {
            lotto = new Lotto(lotto, lottosGenerator.generate());
        }

        return lotto;
    }
}