package magic.test;

import magic.model.MagicDuel;
import magic.model.MagicGame;
import magic.model.MagicPlayer;
import magic.model.MagicDeckProfile;
import magic.model.phase.*;
import magic.ai.MagicAIImpl;

class TestIssue760 extends TestGameBuilder {
    public MagicGame getGame() {
        final MagicDuel duel=createDuel(MagicAIImpl.MCTSC, 6);
        final MagicGame game=duel.nextGame();
        game.setPhase(MagicUpkeepPhase.getInstance());
        final MagicPlayer player=game.getPlayer(0);
        final MagicPlayer opponent=game.getPlayer(1);

        MagicPlayer P = player;

        P.setLife(1);
        addToLibrary(P, "Forest", 20);
        createPermanent(P, "Forest", false, 8);
        createPermanent(P, "Chromatic Lantern", false, 1);
        addToHand(P, "Fiery Temper", 8);

        P = opponent;

        P.setLife(1);
        addToLibrary(P, "Forest", 20);
        createPermanent(P, "Forest", false, 1);

        return game;
    }
}
