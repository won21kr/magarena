[
    new MagicWhenOtherPutIntoGraveyardFromPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent otherPermanent) {
            final MagicPlayer player = permanent.getController();
            return (otherPermanent != permanent &&
                    otherPermanent.isCreature() && 
                    otherPermanent.hasAbility(MagicAbility.Shadow)) ?
                new MagicEvent(
                    permanent,
                    player,
                    this,
                    "PN puts a +1/+1 counter on SN."
                ) :
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicChangeCountersAction(event.getPermanent(),MagicCounterType.PlusOne,1,true));
        }
    }
]
