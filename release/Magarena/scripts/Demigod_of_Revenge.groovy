[
    new ThisSpellIsCastTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicCardOnStack cardOnStack) {
            return new MagicEvent(
                cardOnStack,
                this,
                "PN returns all cards named SN from his or her graveyard to the battlefield."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final List<MagicCard> cards = CARD_FROM_GRAVEYARD.filter(event);
            for (final MagicCard card : cards) {
                if (card.getName().equals(event.getSource().getName())) {
                    game.doAction(new ReanimateAction(
                        card,
                        event.getPlayer()
                    ));
                }
            }
        }
    }
]
