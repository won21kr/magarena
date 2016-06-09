package magic.model.condition;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import magic.model.ARG;
import magic.model.MagicPermanent;
import magic.model.MagicAbility;
import magic.model.MagicColor;
import magic.model.MagicCounterType;
import magic.model.event.MagicMatchedCostEvent;
import magic.model.target.MagicTargetFilterFactory;
import magic.model.target.MagicTargetFilter;

public enum MagicConditionParser {

    YouControlOr("you control a(n)? " + ARG.WORD1 +" or a(n)? "+ ARG.WORD2) {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.YouControlOr(
                MagicTargetFilterFactory.Permanent(ARG.word1(arg)),
                MagicTargetFilterFactory.Permanent(ARG.word2(arg))
            );
        }
    },
    YouControl("you control a(n)? " + ARG.WORDRUN) {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.YouControl(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg))
            );
        }
    },
    OpponentControl("an opponent controls a(n)? " + ARG.WORDRUN) {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.OpponentControl(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg))
            );
        }
    },
    DefenderControl("defending player controls a(n)? " + ARG.WORDRUN) {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.OpponentControl(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg))
            );
        }
    },
    DefenderPoisoned("defending player is poisoned") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.DEFENDING_POISONED;
        }
    },
    YouControlAnother("you control another " + ARG.WORDRUN) {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.YouControlAnother(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg))
            );
        }
    },
    ControlAtLeast("you control " + ARG.AMOUNT + " or more " + ARG.WORDRUN) {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.YouControlAtLeast(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg)),
                ARG.amount(arg)
            );
        }
    },
    ControlAtMost("you control " + ARG.AMOUNT + " or fewer " + ARG.WORDRUN) {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.YouControlAtMost(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg)),
                ARG.amount(arg)
            );
        }
    },
    OppControlAtLeast("an opponent controls " + ARG.AMOUNT + " or more " + ARG.WORDRUN) {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.OppControlAtLeast(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg)),
                ARG.amount(arg)
            );
        }
    },
    ControlNone("you control no " + ARG.WORDRUN) {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.YouControlNone(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg))
            );
        }
    },
    Threshold("seven or more cards are in your graveyard") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.THRESHOLD_CONDITION;
        }
    },
    YourGraveyardAtLeast("there are " + ARG.AMOUNT + " or more cards in your graveyard") {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = ARG.amount(arg);
            return MagicConditionFactory.GraveyardAtLeast(amount);
        }
    },
    Delirium("there are four or more card types among cards in your graveyard") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.DELIRIUM_CONDITION;
        }
    },
    YourDevotionAtLeast("you have at least " + ARG.NUMBER + " devotion to "+ ARG.COLOR) {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = ARG.amount(arg);
            final MagicColor color = MagicColor.valueOf(ARG.color(arg));
            return MagicConditionFactory.DevotionAtLeast(color, amount);
        }
    },
    YourDevotion("you have devotion to " + ARG.COLOR) {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = 1;
            final MagicColor color = MagicColor.getColor(ARG.color(arg));
            return MagicConditionFactory.DevotionAtLeast(color, amount);
        }
    },
    SpellMastery("there are two or more instant and/or sorcery cards in your graveyard") {
        public MagicCondition toCondition(final Matcher arg) { return MagicCondition.SPELL_MASTERY_CONDITION; }
    },
    ExactlySeven("you have exactly seven cards in hand") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.EXACTLY_SEVEN_CARDS_IN_HAND_CONDITION;
        }
    },
    HandSize("you have " + ARG.AMOUNT + " or more cards in hand") {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = ARG.amount(arg);
            return MagicConditionFactory.HandAtLeast(amount);
        }
    },
    Hellbent("you have no cards in hand") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.HELLBENT;
        }
    },
    OpponentHellbent("an opponent has no cards in hand") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.OPPONENT_HELLBENT;
        }
    },
    CountersAtMost("(SN|it) has " + ARG.AMOUNT + " or fewer " + ARG.WORD1 + " counters on (it|him)") {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = ARG.amount(arg);
            final MagicCounterType counterType = MagicCounterType.getCounterRaw(ARG.word1(arg));
            return MagicConditionFactory.CounterAtMost(counterType, amount);
        }
    },
    CountersAtLeast("(SN|it) has " + ARG.AMOUNT + " or more " + ARG.WORD1 + " counters on it") {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = ARG.amount(arg);
            final MagicCounterType counterType = MagicCounterType.getCounterRaw(ARG.word1(arg));
            return MagicConditionFactory.CounterAtLeast(counterType, amount);
        }
    },
    CountersAtLeastAlt("there are " + ARG.AMOUNT + " or more " + ARG.WORD1 + " counters on SN") {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = ARG.amount(arg);
            final MagicCounterType counterType = MagicCounterType.getCounterRaw(ARG.word1(arg));
            return MagicConditionFactory.CounterAtLeast(counterType, amount);
        }
    },
    CountersAtLeastOne("(SN|it) has a(n)? " + ARG.WORD1 + " counter on it") {
        public MagicCondition toCondition(final Matcher arg) {
            final MagicCounterType counterType = MagicCounterType.getCounterRaw(ARG.word1(arg));
            return MagicConditionFactory.CounterAtLeast(counterType, 1);
        }
    },
    CountersEqual("there (is|are) exactly " + ARG.AMOUNT + " " + ARG.WORD1 + " counter(s)? on SN") {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = ARG.amount(arg);
            final MagicCounterType counterType = MagicCounterType.getCounterRaw(ARG.word1(arg));
            return MagicConditionFactory.CounterEqual(counterType, amount);
        }
    },
    CountersNone("((SN|it) (has|had)|there are) no " + ARG.WORD1 + " counters on (it|SN)") {
        public MagicCondition toCondition(final Matcher arg) {
            final MagicCounterType counterType = MagicCounterType.getCounterRaw(ARG.word1(arg));
            return MagicConditionFactory.CounterEqual(counterType, 0);
        }
    },
    HasEquipped("SN is being equipped") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.HAS_EQUIPPED_CREATURE;
        }
    },
    IsEquipped("(SN is|it's) equipped") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.IS_EQUIPPED;
        }
    },
    IsEnchanted("(SN is|it's) enchanted") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.IS_ENCHANTED;
        }
    },
    IsEnchantment("(SN is|it's) an enchantment") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.IS_ENCHANTMENT;
        }
    },
    IsNotEnchantment("(SN is|it's) not an enchantment") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.IS_NOT_ENCHANTMENT;
        }
    },
    IsPermanent("(SN is|it's) a(n)? " + ARG.WORDRUN) {
        public MagicCondition toCondition(final Matcher arg) {
            final MagicTargetFilter<MagicPermanent> filter = MagicTargetFilterFactory.Permanent(ARG.wordrun(arg));
            return MagicConditionFactory.SelfIs(filter);
        }
    },
    EquippedIsA("equipped creature is( a| an)? " + ARG.WORDRUN) {
        public MagicCondition toCondition(final Matcher arg) {
            final MagicTargetFilter<MagicPermanent> filter = MagicTargetFilterFactory.Permanent(ARG.wordrun(arg));
            return MagicConditionFactory.EquippedIs(filter);
        }
    },
    EnchantedIsMountain("enchanted land is a basic Mountain") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.ENCHANTED_IS_BASIC_MOUNTAIN;
        }
    },
    EnchantedIsA("enchanted creature is a(n)? " + ARG.WORDRUN) {
        public MagicCondition toCondition(final Matcher arg) {
            final MagicTargetFilter<MagicPermanent> filter = MagicTargetFilterFactory.Permanent(ARG.wordrun(arg));
            return MagicConditionFactory.EnchantedIs(filter);
        }
    },
    EnchantedIsColor("enchanted creature is " + ARG.COLOR) {
        public MagicCondition toCondition(final Matcher arg) {
            final MagicTargetFilter<MagicPermanent> filter = MagicTargetFilterFactory.Permanent(ARG.color(arg));
            return MagicConditionFactory.EnchantedIs(filter);
        }
    },
    IsUntapped("(SN is|it's) untapped") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.UNTAPPED_CONDITION;
        }
    },
    IsTapped("(SN is|it's) tapped") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.TAPPED_CONDITION;
        }
    },
    IsMonstrous("(SN is|it's) monstrous") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.IS_MONSTROUS_CONDITION;
        }
    },
    IsRenowned("(SN is|it's) renowned") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.IS_RENOWNED_CONDITION;
        }
    },
    IsBlocked("(SN is|it's) blocked") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.IS_BLOCKED_CONDITION;
        }
    },
    IsAttacking("(SN is|it's) attacking") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.IS_ATTACKING_CONDITION;
        }
    },
    AttackingAlone("it's attacking alone") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.IS_ATTACKING_ALONE_CONDITION;
        }
    },
    HasDefender("it has defender") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.HasAbility(MagicAbility.Defender);
        }
    },
    HasFlying("SN has flying") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.HasAbility(MagicAbility.Flying);
        }
    },
    NoCardsInGraveyard("there are no cards in your graveyard") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.EMPTY_GRAVEYARD_CONDITION;
        }
    },
    LibraryWithLEQ20Cards("a library has twenty or fewer cards in it") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.LIBRARY_HAS_20_OR_LESS_CARDS_CONDITION;
        }
    },
    OpponentGraveyardWithGEQ10Cards("an opponent has ten or more cards in his or her graveyard") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.OPP_GRAVEYARD_WITH_10_OR_MORE_CARDS_CONDTITION;
        }
    },
    OpponentNotControlWhiteOrBlueCreature("no opponent controls a white or blue creature") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.OPP_NOT_CONTROL_WHITE_OR_BLUE_CREATURE_CONDITION;
        }
    },
    MostCardsInHand("you have more cards in hand than each opponent") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.MOST_CARDS_IN_HAND_CONDITION;
        }
    },
    WarriorCardInGraveyard("a Warrior card is in your graveyard") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.HAS_WARRIOR_IN_GRAVEYARD;
        }
    },
    ArtifactCardInGraveyard("an artifact card is in your graveyard") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.HAS_ARTIFACT_IN_GRAVEYARD;
        }
    },
    NoOpponentCreatures("no opponent controls a creature") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.OPP_NOT_CONTROL_CREATURE_CONDITION;
        }
    },
    IsYourTurn("it's your turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.YOUR_TURN_CONDITION;
        }
    },
    IsNotYourTurn("it's not your turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.NOT_YOUR_TURN_CONDITION;
        }
    },
    YouLifeOrMore("you have "+ARG.NUMBER+" or more life") {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = ARG.number(arg);
            return MagicConditionFactory.YouLifeAtLeast(amount);
        }
    },
    YouLifeOrLess("you have "+ARG.NUMBER+" or less life") {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = ARG.number(arg);
            return MagicConditionFactory.YouLifeOrLess(amount);
        }
    },
    You30LifeOrMoreOpponent10LifeOrLess("you have 30 or more life and an opponent has 10 or less life") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.YOU_30_OR_MORE_OPPPONENT_10_OR_LESS_LIFE;
        }
    },
    OpponentTenLifeOrLess("an opponent has 10 or less life") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.OPPONENT_TEN_OR_LESS_LIFE;
        }
    },
    NoSpellsCastLastTurn("no spells were cast last turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.NO_SPELLS_CAST_LAST_TURN;
        }
    },
    TwoOrMoreSpellsCastByPlayerLastTurn("a player cast two or more spells last turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.TWO_OR_MORE_SPELLS_CAST_BY_PLAYER_LAST_TURN;
        }
    },
    Once("once each turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.ABILITY_ONCE_CONDITION;
        }
    },
    Twice("no more than twice each turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.ABILITY_TWICE_CONDITION;
        }
    },
    Thrice("no more than three times each turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.ABILITY_THRICE_CONDITION;
        }
    },
    BeforeYourAttack("during your turn, before attackers are declared") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.BEFORE_YOUR_ATTACK_CONDITION;
        }
    },
    BeenAttacked("you've been attacked this step") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.BEEN_ATTACKED;
        }
    },
    DuringAttack("during the declare attackers step") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.DECLARE_ATTACKERS;
        }
    },
    DuringEndOfCombat("during the end of combat step") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.END_OF_COMBAT_CONDITION;
        }
    },
    DuringCombatAfterBlockers("during combat after blockers are declared") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.DURING_COMBAT_AFTER_BLOCKERS;
        }
    },
    DuringCombatBeforeBlockers("during combat before blockers are declared") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.DURING_COMBAT_AFTER_BLOCKERS;
        }
    },
    YourTurn("during your turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.YOUR_TURN_CONDITION;
        }
    },
    YourUpkeep("during your upkeep") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.YOUR_UPKEEP_CONDITION;
        }
    },
    OpponentsUpkeep("during an opponent's upkeep") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.OPPONENTS_UPKEEP_CONDITION;
        }
    },
    Sorcery("any time you could cast a sorcery") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.SORCERY_CONDITION;
        }
    },
    DuringCombat("during combat") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.DURING_COMBAT;
        }
    },
    NoneOnBattlefield("no " + ARG.WORDRUN + " are on the battlefield") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.BattlefieldEqual(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg)), 0
            );
        }
    },
    NoneOnBattlefieldAlt("there are no " + ARG.WORDRUN + " on the battlefield") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.BattlefieldEqual(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg)), 0
            );
        }
    },
    AtLeastOneOnBattlefield("there is (a|an) " + ARG.WORDRUN + " on the battlefield") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.BattlefieldAtLeast(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg)), 1
            );
        }
    },
    FiveOrMoreIslands("there are " + ARG.AMOUNT + " or more " + ARG.WORDRUN + " on the battlefield") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.BattlefieldAtLeast(
                MagicTargetFilterFactory.Permanent(ARG.wordrun(arg)), ARG.amount(arg)
            );
        }
    },
    MoreCreaturesThanDefending("you control more creatures than defending player") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.MORE_CREATURES_THAN_DEFENDING;
        }
    },
    MoreCreaturesThanAttacking("you control more creatures than attacking player") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.MORE_CREATURES_THAN_ATTACKING;
        }
    },
    MoreLandsThanDefending("you control more lands than defending player") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.MORE_LANDS_THAN_DEFENDING;
        }
    },
    MoreLandsThanAttacking("you control more lands than attacking player") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.MORE_LANDS_THAN_ATTACKING;
        }
    },
    OpponentMoreLands("an opponent controls more lands than you") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.OPP_MORE_LANDS;
        }
    },
    Morbid("a creature died this turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.CREATURE_DIED_THIS_TURN;
        }
    },
    YouGainedLifeOrMore("you gained "+ARG.NUMBER+" or more life this turn") {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = ARG.number(arg);
            return MagicConditionFactory.YouGainLifeOrMore(amount);
        }
    },
    OpponentGainedLife("an opponent gained life this turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.OpponentGainLifeOrMore(1);
        }
    },
    OpponentLostLife("an opponent lost life this turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicConditionFactory.OpponentLoseLifeOrMore(1);
        }
    },
    OpponentLostLifeOrMore("an opponent lost "+ARG.NUMBER+" or more life this turn") {
        public MagicCondition toCondition(final Matcher arg) {
            final int amount = ARG.number(arg);
            return MagicConditionFactory.OpponentLoseLifeOrMore(amount);
        }
    },
    OpponentWasDealtDamage("an opponent was dealt damage this turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.OPPONENT_WAS_DEALT_DAMAGE;
        }
    },
    YouAttackedWithCreature("you attacked with a creature this turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.YOU_ATTACKED_WITH_CREATURE;
        }
    },
    CreatureInAGraveyard("there is a creature card in a graveyard") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.CREATURE_IN_A_GRAVEYARD;
        }
    },
    CreatureInYourGraveyard("you have a creature card in your graveyard") {
        public MagicCondition toCondition(final Matcher arf) { return MagicCondition.HAS_CREATURE_IN_GRAVEYARD;}
    },
    CreatureInYourHand("you have a creature card in your hand") {
        public MagicCondition toCondition(final Matcher arf) { return MagicCondition.HAS_CREATURE_IN_HAND;}
    },
    ArtifactInYourGraveyard("you have an artifact card in your graveyard") {
        public MagicCondition toCondition(final Matcher arf) { return MagicCondition.HAS_ARTIFACT_IN_GRAVEYARD;}
    },
    EquipmentInYourHand("you have an equipment card in your hand") {
        public MagicCondition toCondition(final Matcher arf) { return MagicCondition.HAS_EQUIPMENT_IN_HAND;}
    },
    InstantOrSorceryInGraveyard("there is an instant or sorcery card in a graveyard") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.INSTANT_OR_SORCERY_IN_A_GRAVEYARD;
        }
    },
    Formidable("creatures you control have total power 8 or greater") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.FORMIDABLE;
        }
    },
    CastItFromHand("you cast it from your hand") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.CAST_FROM_HAND;
        }
    },
    WasKicked("SN was kicked") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.WAS_KICKED;
        }
    },
    SurgePaid("SN's surge cost was paid") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.WAS_KICKED;
        }
    },
    ControlledSinceLastTurn("you've controlled SN continuously since the beginning of your most recent turn") {
        public MagicCondition toCondition(final Matcher arg) {
            return MagicCondition.CONTROL_SINCE_LAST_TURN;
        }
    },
    ;

    private final Pattern pattern;

    private MagicConditionParser(final String regex) {
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public Matcher matcher(final String rule) {
        return pattern.matcher(rule);
    }

    public abstract MagicCondition toCondition(final Matcher arg);

    public static final MagicCondition build(final String cost) {
        final boolean aiOnly = cost.startsWith("with AI ");
        final String processed = cost
            .replaceFirst("^with AI ", "")
            .replaceFirst("^only ", "")
            .replaceFirst("^if ", "")
            .replaceFirst("\\.$", "");
        for (final MagicConditionParser rule : values()) {
            final Matcher matcher = rule.matcher(processed);
            if (matcher.matches()) {
                final MagicCondition cond = rule.toCondition(matcher);
                return aiOnly ? new MagicArtificialCondition(cond) : cond;
            }
        }
        throw new RuntimeException("unknown condition \"" + cost + "\"");
    }

    public static MagicCondition[] buildCast(final String costs) {
        final String[] splitCosts = costs.split(" and ");
        final MagicCondition[] conds = new MagicCondition[splitCosts.length + 1];
        conds[0] = MagicCondition.CARD_CONDITION;
        for (int i = 0; i < splitCosts.length; i++) {
            conds[i + 1] = build(splitCosts[i]);
        }
        return conds;
    }

    public static List<MagicMatchedCostEvent> buildCost(final String costs) {
        final String[] splitCosts = costs.split(" and ");
        final List<MagicMatchedCostEvent> matched = new LinkedList<MagicMatchedCostEvent>();
        for (String cost : splitCosts) {
            matched.add(build(cost));
        }
        return matched;
    }
}
