/*
 * Copyright (c) 2020, Zoinkwiz
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.questhelper.quests.thedigsite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.runelite.api.ItemID;
import net.runelite.api.NpcID;
import net.runelite.api.NullObjectID;
import net.runelite.api.ObjectID;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.questhelper.QuestDescriptor;
import net.runelite.client.plugins.questhelper.QuestHelperQuest;
import net.runelite.client.plugins.questhelper.Zone;
import net.runelite.client.plugins.questhelper.panel.PanelDetails;
import net.runelite.client.plugins.questhelper.questhelpers.BasicQuestHelper;
import net.runelite.client.plugins.questhelper.requirements.ItemRequirement;
import net.runelite.client.plugins.questhelper.steps.ConditionalStep;
import net.runelite.client.plugins.questhelper.steps.DetailedQuestStep;
import net.runelite.client.plugins.questhelper.steps.ItemStep;
import net.runelite.client.plugins.questhelper.steps.NpcStep;
import net.runelite.client.plugins.questhelper.steps.ObjectStep;
import net.runelite.client.plugins.questhelper.steps.QuestStep;
import net.runelite.client.plugins.questhelper.steps.conditional.ConditionForStep;
import net.runelite.client.plugins.questhelper.steps.conditional.Conditions;
import net.runelite.client.plugins.questhelper.steps.conditional.ItemRequirementCondition;
import net.runelite.client.plugins.questhelper.steps.conditional.LogicType;
import net.runelite.client.plugins.questhelper.steps.conditional.ObjectCondition;
import net.runelite.client.plugins.questhelper.steps.conditional.VarbitCondition;
import net.runelite.client.plugins.questhelper.steps.conditional.WidgetTextCondition;
import net.runelite.client.plugins.questhelper.steps.conditional.ZoneCondition;

@QuestDescriptor(
	quest = QuestHelperQuest.THE_DIG_SITE
)
public class TheDigSite extends BasicQuestHelper
{
	ItemRequirement pestleAndMortar, vialHighlighted, tinderbox, tea, ropes2, rope, opal, charcoal, leatherBoots, leatherGloves, specimenBrush, specimenJar, panningTray,
		trowel, varrock2, digsiteTeleports, sealedLetter, specialCup, teddybear, skull, nitro, nitrate, chemicalCompound, groundCharcoal, invitation, talisman,
		mixedChemicals, mixedChemicals2, arcenia, powder, liquid, tablet, key, unstampedLetter, pick, trowelHighlighted, tinderboxHighlighted, chemicalCompoundHighlighted;

	ConditionForStep hasTeddy, hasTray, hasSkull, hasBrush, hasSpecialCup, talkedToFemaleStudent, talkedToOrangeStudent, talkedToGreenStudent, talkedToGuide, letterStamped,
		femaleStudentQ1Learnt, orangeStudentQ1Learnt, greenStudentQ1Learnt, femaleStudentQ2Learnt, orangeStudentQ2Learnt, greenStudentQ2Learnt, femaleStudentQ3Learnt,
		femaleExtorting, orangeStudentQ3Learnt, greenStudentQ3Learnt, syncedUp, syncedUp2, syncedUp3, hasJar, hasPick, hasTalisman, givenTalismanIn, rope1Added, rope2Added,
		inUndergroundTemple1, inDougRoom, hasArcenia, hasChemicalCompound, hasMixedChemicals2, hasMixedChemicals, hasNitrate, hasNitro, hasPowder, hasLiquid, openedBarrel,
		searchedBricks, hasKeyOrPowderOrMixtures, openPowderChestNearby, inUndergroundTemple2, hasTablet;

	QuestStep talkToExaminer, talkToHaig, talkToExaminer2, searchBush, takeTray, talkToGuide, panWater, pickpocketWorkmen, talkToFemaleStudent, talkToFemaleStudent2,
		talkToOrangeStudent, talkToOrangeStudent2, talkToGreenStudent, talkToGreenStudent2, takeTest1, talkToFemaleStudent3, talkToOrangeStudent3, talkToGreenStudent3,
		takeTest2, talkToFemaleStudent4, takeTest3, getJar, getPick, getBrush, digForTalisman, talkToExpert, useInvitationOnWorkman, useRopeOnWinch, goDownWinch, pickUpRoot,
		searchBricks, goUpRope, goDownToDoug, talkToDoug, goUpFromDoug, unlockChest, searchChest, useTrowelOnBarrel, useVialOnBarrel, grindCharcoal, usePowderOnExpert,
		useLiquidOnExpert, mixNitroWithNitrate, addCharcoal, addRoot, goDownToExplode, useCompound, useTinderbox, takeTablet, useTabletOnExpert, syncStep, talkToFemaleStudent5,
		talkToOrangeStudent4, talkToGreenStudent4, useRopeOnWinch2, goDownToExplode2, goDownForTablet, goUpWithTablet;

	Zone undergroundTemple1, dougRoom, undergroundTemple2;

	@Override
	public Map<Integer, QuestStep> loadSteps()
	{
		loadZones();
		setupItemRequirements();
		setupConditions();
		setupSteps();
		Map<Integer, QuestStep> steps = new HashMap<>();

		steps.put(0, talkToExaminer);

		ConditionalStep returnWithLetter = new ConditionalStep(this, talkToHaig);
		returnWithLetter.addStep(letterStamped, talkToExaminer2);
		steps.put(1, returnWithLetter);

		ConditionalStep goTakeTest1 = new ConditionalStep(this, syncStep);
		goTakeTest1.addStep(new Conditions(femaleStudentQ1Learnt, orangeStudentQ1Learnt, greenStudentQ1Learnt), takeTest1);
		goTakeTest1.addStep(new Conditions(femaleStudentQ1Learnt, orangeStudentQ1Learnt, talkedToGreenStudent), talkToGreenStudent2);
		goTakeTest1.addStep(new Conditions(femaleStudentQ1Learnt, orangeStudentQ1Learnt, hasSkull, hasBrush), talkToGreenStudent);

		goTakeTest1.addStep(new Conditions(femaleStudentQ1Learnt, talkedToOrangeStudent, hasSkull, hasBrush), talkToOrangeStudent2);
		goTakeTest1.addStep(new Conditions(femaleStudentQ1Learnt, hasSpecialCup, hasSkull, hasBrush), talkToOrangeStudent);

		goTakeTest1.addStep(new Conditions(talkedToFemaleStudent, hasSpecialCup, hasSkull, hasBrush), talkToFemaleStudent2);
		goTakeTest1.addStep(new Conditions(hasTeddy, hasSpecialCup, hasSkull, hasBrush), talkToFemaleStudent);

		goTakeTest1.addStep(new Conditions(syncedUp, hasTeddy, hasSpecialCup), pickpocketWorkmen);
		goTakeTest1.addStep(new Conditions(syncedUp, hasTeddy, hasTray, talkedToGuide), panWater);
		goTakeTest1.addStep(new Conditions(syncedUp, hasTeddy, hasTray), talkToGuide);
		goTakeTest1.addStep(new Conditions(syncedUp, hasTeddy), takeTray);
		goTakeTest1.addStep(syncedUp, searchBush);
		steps.put(2, goTakeTest1);

		ConditionalStep goTakeTest2 = new ConditionalStep(this, syncStep);
		goTakeTest2.addStep(new Conditions(syncedUp2, femaleStudentQ2Learnt, orangeStudentQ2Learnt, greenStudentQ2Learnt), takeTest2);
		goTakeTest2.addStep(new Conditions(syncedUp2, femaleStudentQ2Learnt, orangeStudentQ2Learnt), talkToGreenStudent3);
		goTakeTest2.addStep(new Conditions(syncedUp2, femaleStudentQ2Learnt), talkToOrangeStudent3);
		goTakeTest2.addStep(syncedUp2, talkToFemaleStudent3);
		steps.put(3, goTakeTest2);

		ConditionalStep goTakeTest3 = new ConditionalStep(this, syncStep);
		goTakeTest3.addStep(new Conditions(femaleStudentQ3Learnt, orangeStudentQ3Learnt, greenStudentQ3Learnt), takeTest3);
		goTakeTest3.addStep(new Conditions(syncedUp3, femaleStudentQ3Learnt, orangeStudentQ3Learnt), talkToGreenStudent4);
		goTakeTest3.addStep(new Conditions(syncedUp3, femaleStudentQ3Learnt), talkToOrangeStudent4);
		goTakeTest3.addStep(new Conditions(syncedUp3, femaleExtorting), talkToFemaleStudent5);
		goTakeTest3.addStep(syncedUp2, talkToFemaleStudent4);
		steps.put(4, goTakeTest3);

		ConditionalStep findTalisman = new ConditionalStep(this, getJar);
		findTalisman.addStep(new Conditions(givenTalismanIn), useInvitationOnWorkman);
		findTalisman.addStep(new Conditions(hasTalisman), talkToExpert);
		findTalisman.addStep(new Conditions(hasJar, hasPick, hasBrush), digForTalisman);
		findTalisman.addStep(new Conditions(hasJar, hasPick), getBrush);
		findTalisman.addStep(hasJar, getPick);
		steps.put(5, findTalisman);

		ConditionalStep learnHowToMakeExplosives = new ConditionalStep(this, useRopeOnWinch2);
		learnHowToMakeExplosives.addStep(inDougRoom, talkToDoug);
		learnHowToMakeExplosives.addStep(new Conditions(inUndergroundTemple1, hasArcenia), goUpRope);
		learnHowToMakeExplosives.addStep(inUndergroundTemple1, pickUpRoot);
		learnHowToMakeExplosives.addStep(new Conditions(rope2Added), goDownToDoug);

		ConditionalStep makeExplosives = new ConditionalStep(this, goDownWinch);
		makeExplosives.addStep(new Conditions(hasChemicalCompound, inUndergroundTemple1), useCompound);

		makeExplosives.addStep(inDougRoom, goUpFromDoug);
		makeExplosives.addStep(new Conditions(inUndergroundTemple1, hasArcenia), goUpRope);
		makeExplosives.addStep(inUndergroundTemple1, pickUpRoot);

		makeExplosives.addStep(new Conditions(hasChemicalCompound), goDownToExplode);
		makeExplosives.addStep(new Conditions(hasArcenia, hasMixedChemicals2), addRoot);
		makeExplosives.addStep(new Conditions(hasArcenia, hasMixedChemicals), addCharcoal);
		makeExplosives.addStep(new Conditions(hasArcenia, hasNitrate, hasNitro), mixNitroWithNitrate);
		makeExplosives.addStep(new Conditions(hasArcenia, hasPowder, hasNitro), usePowderOnExpert);
		makeExplosives.addStep(new Conditions(hasArcenia, hasPowder, hasLiquid), useLiquidOnExpert);
		makeExplosives.addStep(new Conditions(hasArcenia, hasPowder, hasLiquid), useVialOnBarrel);
		makeExplosives.addStep(new Conditions(hasArcenia, hasPowder, openedBarrel), useVialOnBarrel);
		makeExplosives.addStep(new Conditions(hasArcenia, hasPowder), useTrowelOnBarrel);
		makeExplosives.addStep(new Conditions(openPowderChestNearby, hasArcenia), searchChest);
		makeExplosives.addStep(hasArcenia, unlockChest);

		ConditionalStep discovery = new ConditionalStep(this, useRopeOnWinch);
		discovery.addStep(hasKeyOrPowderOrMixtures, makeExplosives);
		discovery.addStep(searchedBricks, learnHowToMakeExplosives);
		discovery.addStep(new Conditions(inUndergroundTemple1, hasArcenia), searchBricks);
		discovery.addStep(inUndergroundTemple1, pickUpRoot);
		discovery.addStep(rope1Added, goDownWinch);
		steps.put(6, discovery);

		ConditionalStep explodeWall = new ConditionalStep(this, goDownToExplode2);
		explodeWall.addStep(inUndergroundTemple1, useTinderbox);
		steps.put(7, explodeWall);

		ConditionalStep completeQuest = new ConditionalStep(this, goDownForTablet);
		completeQuest.addStep(new Conditions(hasTablet, inUndergroundTemple2), goUpWithTablet);
		completeQuest.addStep(new Conditions(hasTablet), useTabletOnExpert);
		completeQuest.addStep(inUndergroundTemple2, takeTablet);
		steps.put(8, completeQuest);

		return steps;
	}

	public void setupItemRequirements()
	{
		pestleAndMortar = new ItemRequirement("Pestle and mortar", ItemID.PESTLE_AND_MORTAR);
		vialHighlighted = new ItemRequirement("Vial", ItemID.VIAL);
		vialHighlighted.setHighlightInInventory(true);
		tinderbox = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);
		tinderboxHighlighted = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);
		tinderboxHighlighted.setHighlightInInventory(true);
		tea = new ItemRequirement("Cup of tea", ItemID.CUP_OF_TEA);
		ropes2 = new ItemRequirement("Rope", ItemID.ROPE, 2);
		rope = new ItemRequirement("Rope", ItemID.ROPE);
		rope.setHighlightInInventory(true);
		opal = new ItemRequirement("Opal", ItemID.OPAL);
		opal.setTip("You can get one by panning at the Digsite");
		opal.addAlternates(ItemID.UNCUT_OPAL);
		charcoal = new ItemRequirement("Charcoal", ItemID.CHARCOAL);
		leatherBoots = new ItemRequirement("Leather boots", ItemID.LEATHER_BOOTS, 1, true);
		leatherGloves = new ItemRequirement("Leather gloves", ItemID.LEATHER_GLOVES, 1, true);
		specimenBrush = new ItemRequirement("Specimen brush", ItemID.SPECIMEN_BRUSH);
		specimenJar = new ItemRequirement("Specimen jar", ItemID.SPECIMEN_JAR);
		panningTray = new ItemRequirement("Panning tray", ItemID.PANNING_TRAY);
		panningTray.addAlternates(ItemID.PANNING_TRAY_678, ItemID.PANNING_TRAY_679);
		trowel = new ItemRequirement("Trowel", ItemID.TROWEL);
		trowel.setTip("You can get another from one of the Examiners");
		trowelHighlighted = new ItemRequirement("Trowel", ItemID.TROWEL);
		trowelHighlighted.setHighlightInInventory(true);
		trowelHighlighted.setTip("You can get another from one of the Examiners");
		pick = new ItemRequirement("Rock pick", ItemID.ROCK_PICK);
		varrock2 = new ItemRequirement("Varrock teleports", ItemID.VARROCK_TELEPORT, 2);
		digsiteTeleports = new ItemRequirement("Digsite teleports", -1, -1);
		sealedLetter = new ItemRequirement("Sealed letter", ItemID.SEALED_LETTER);
		sealedLetter.setTip("You can get another from Curator Haig in the Varrock Museum");
		specialCup = new ItemRequirement("Special cup", ItemID.SPECIAL_CUP);
		teddybear = new ItemRequirement("Teddy", ItemID.TEDDY);
		skull = new ItemRequirement("Animal skull", ItemID.ANIMAL_SKULL);
		nitro = new ItemRequirement("Nitroglycerin", ItemID.NITROGLYCERIN);
		nitro.setHighlightInInventory(true);
		nitrate = new ItemRequirement("Ammonium nitrate", ItemID.AMMONIUM_NITRATE);
		nitrate.setHighlightInInventory(true);
		chemicalCompound = new ItemRequirement("Chemical compound", ItemID.CHEMICAL_COMPOUND);
		chemicalCompoundHighlighted = new ItemRequirement("Chemical compound", ItemID.CHEMICAL_COMPOUND);
		chemicalCompoundHighlighted.setHighlightInInventory(true);
		groundCharcoal = new ItemRequirement("Ground charcoal", ItemID.GROUND_CHARCOAL);
		groundCharcoal.setTip("You can make this by use a pestle and mortar on some charcoal. You can get charcoal from one of the specimen trays in the Digsite");
		groundCharcoal.setHighlightInInventory(true);
		invitation = new ItemRequirement("Invitation letter", ItemID.INVITATION_LETTER);
		invitation.setTip("You can get another from the Archaeological expert");
		invitation.setHighlightInInventory(true);
		talisman = new ItemRequirement("Ancient talisman", ItemID.ANCIENT_TALISMAN);
		mixedChemicals = new ItemRequirement("Mixed chemicals", ItemID.MIXED_CHEMICALS);
		mixedChemicals.setHighlightInInventory(true);
		mixedChemicals2 = new ItemRequirement("Mixed chemicals", ItemID.MIXED_CHEMICALS_706);
		mixedChemicals2.setHighlightInInventory(true);
		arcenia = new ItemRequirement("Arcenia root", ItemID.ARCENIA_ROOT);
		arcenia.setHighlightInInventory(true);

		powder = new ItemRequirement("Chemical powder", ItemID.CHEMICAL_POWDER);
		powder.setHighlightInInventory(true);
		liquid = new ItemRequirement("Unidentified liquid", ItemID.UNIDENTIFIED_LIQUID);
		liquid.setHighlightInInventory(true);
		tablet = new ItemRequirement("Stone tablet", ItemID.STONE_TABLET);
		tablet.setHighlightInInventory(true);
		key = new ItemRequirement("Chest key", ItemID.CHEST_KEY_709);
		key.setHighlightInInventory(true);
		unstampedLetter = new ItemRequirement("Unstamped letter", ItemID.UNSTAMPED_LETTER);
		unstampedLetter.setTip("You can get another from the Exam Centre's examiners");
	}

	public void loadZones()
	{
		undergroundTemple1 = new Zone(new WorldPoint(3359, 9800, 0), new WorldPoint(3393, 9855, 0));
		undergroundTemple2 = new Zone(new WorldPoint(3360, 9734, 0), new WorldPoint(3392, 9790, 0));
		dougRoom = new Zone(new WorldPoint(3340, 9809, 0), new WorldPoint(3357, 9826, 0));
	}

	public void setupConditions()
	{
		inUndergroundTemple1 = new ZoneCondition(undergroundTemple1);
		inUndergroundTemple2 = new ZoneCondition(undergroundTemple2);
		inDougRoom = new ZoneCondition(dougRoom);

		syncedUp = new Conditions(true, new WidgetTextCondition(119, 2, "The Dig Site"));
		syncedUp2 = new Conditions(true, LogicType.OR, new WidgetTextCondition(119, 2, "The Dig Site"),
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "You got all the questions correct. Well done!"),
			new WidgetTextCondition(217, 4, "Hey! Excellent!"));
		syncedUp3 = new Conditions(true, LogicType.OR, new WidgetTextCondition(119, 2, "The Dig Site"),
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "You got all the questions correct, well done!"),
			new WidgetTextCondition(217, 4, "Great, I'm getting good at this."));

		talkedToGuide = new VarbitCondition(2544, 1);

		// Exam questions 1
		talkedToFemaleStudent = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "Hey! My lucky mascot!"),
			new WidgetTextCondition(119, 3, true, "I should talk to her to see if she can help"));
		femaleStudentQ1Learnt = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "The proper health and safety points are"),
			new WidgetTextCondition(119, 3, true, "She gave me an answer"));

		WidgetTextCondition orangeGivenAnswer1Diary = new WidgetTextCondition(119, 3, true, "He gave me an answer to one of the questions");
		orangeGivenAnswer1Diary.addRange(20, 35);
		talkedToOrangeStudent = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(217, 4, "Look what I found!"),
			new WidgetTextCondition(119, 3, true, "<str>to find it and return it to him."));
		orangeStudentQ1Learnt = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "The people eligible to use the digsite are:"),
			orangeGivenAnswer1Diary);

		WidgetTextCondition greenGivenAnswer1Diary = new WidgetTextCondition(119, 3, true, "He gave me an answer to one of the questions");
		greenGivenAnswer1Diary.addRange(0, 19);

		talkedToGreenStudent = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "Oh wow! You've found it!"),
			new WidgetTextCondition(119, 3, true, "<str>to him; maybe someone has picked it up?"));
		greenStudentQ1Learnt = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "The study of Earth Sciences is:"),
			greenGivenAnswer1Diary);

		// Exam questions 2
		WidgetTextCondition femaleGivenAnswer2Diary = new WidgetTextCondition(119, 3, true, "<str>I need to speak to the student in the purple skirt about");
		femaleGivenAnswer2Diary.addRange(43, 52);
		femaleStudentQ2Learnt = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "Finds handling: Finds must"),
			femaleGivenAnswer2Diary);

		WidgetTextCondition orangeGivenAnswer2Diary = new WidgetTextCondition(119, 3, true, "<str>I need to speak to the student in the orange top about the");
		orangeGivenAnswer2Diary.addRange(43, 52);
		orangeStudentQ2Learnt = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "Correct sample transportation: "),
			orangeGivenAnswer2Diary);

		WidgetTextCondition greenGivenAnswer2Diary = new WidgetTextCondition(119, 3, true, "<str>I need to speak to the student in the green top about the");
		greenGivenAnswer2Diary.addRange(43, 52);
		greenStudentQ2Learnt = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "Correct rock pick usage: Always handle"),
			greenGivenAnswer2Diary);

		// Exam questions 3
		femaleExtorting = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(217, 4, "OK, I'll see what I can turn up for you."),
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "Well, I have seen people get them from panning"),
			new WidgetTextCondition(119, 3, true, "I need to bring her an opal"));
		WidgetTextCondition femaleGivenAnswer3Diary = new WidgetTextCondition(119, 3, true, "<str>I need to speak to the student in the purple skirt about");
		femaleGivenAnswer3Diary.addRange(56, 63);
		femaleStudentQ3Learnt = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "Sample preparation: Samples cleaned"),
			femaleGivenAnswer3Diary);

		WidgetTextCondition orangeGivenAnswer3Diary = new WidgetTextCondition(119, 3, true, "<str>I need to speak to the student in the orange top about the");
		orangeGivenAnswer3Diary.addRange(56, 63);
		orangeStudentQ3Learnt = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "The proper technique for handling bones is: Handle"),
			orangeGivenAnswer3Diary);

		WidgetTextCondition greenGivenAnswer3Diary = new WidgetTextCondition(119, 3, true, "<str>I need to speak to the student in the green top about the");
		greenGivenAnswer3Diary.addRange(56, 63);
		greenStudentQ3Learnt = new Conditions(true, LogicType.OR,
			new WidgetTextCondition(WidgetInfo.DIALOG_NPC_TEXT, "Specimen brush use: Brush carefully"),
			greenGivenAnswer3Diary);

		// 2550 = 1, gotten invite
		// 3644 = 1, gotten invite
		givenTalismanIn = new VarbitCondition(2550, 1);
		rope1Added = new VarbitCondition(2545, 1);
		rope2Added = new VarbitCondition(2546, 1);

		// 45 - 54
		hasTray = new ItemRequirementCondition(panningTray);
		hasTeddy = new Conditions(LogicType.OR, new ItemRequirementCondition(teddybear), talkedToFemaleStudent);
		hasSkull = new Conditions(LogicType.OR, new ItemRequirementCondition(skull), talkedToGreenStudent);
		hasSpecialCup = new Conditions(LogicType.OR, new ItemRequirementCondition(specialCup), talkedToOrangeStudent);
		hasBrush = new ItemRequirementCondition(specimenBrush);
		letterStamped = new VarbitCondition(2552, 1);
		hasJar = new ItemRequirementCondition(specimenJar);
		hasPick = new ItemRequirementCondition(pick);
		hasTalisman = new ItemRequirementCondition(talisman);
		hasArcenia = new ItemRequirementCondition(arcenia);
		hasChemicalCompound = new ItemRequirementCondition(chemicalCompound);
		hasMixedChemicals2 = new ItemRequirementCondition(mixedChemicals2);
		hasMixedChemicals = new ItemRequirementCondition(mixedChemicals);
		hasNitrate = new ItemRequirementCondition(nitrate);
		hasNitro = new ItemRequirementCondition(nitro);
		hasPowder = new ItemRequirementCondition(powder);
		hasLiquid = new ItemRequirementCondition(liquid);
		hasTablet = new ItemRequirementCondition(tablet);

		searchedBricks = new VarbitCondition(2549, 1);
		openPowderChestNearby = new ObjectCondition(ObjectID.CHEST_2360);
		openedBarrel = new VarbitCondition(2547, 1);

		hasKeyOrPowderOrMixtures = new Conditions(LogicType.OR,
			new ItemRequirementCondition(key),
			hasPowder, hasNitrate, hasMixedChemicals, hasMixedChemicals2, hasChemicalCompound, openPowderChestNearby);
	}

	public void setupSteps()
	{
		talkToExaminer = new NpcStep(this, NpcID.EXAMINER, new WorldPoint(3362, 3337, 0), "Talk to an Examiner in the Exam Centre south east of Varrock.", unstampedLetter);
		talkToExaminer.addDialogStep("Can I take an exam?");
		((NpcStep) (talkToExaminer)).addAlternateNpcs(NpcID.EXAMINER_3636, NpcID.EXAMINER_3637);
		talkToHaig = new NpcStep(this, NpcID.CURATOR_HAIG_HALEN, new WorldPoint(3257, 3448, 0), "Talk to Curator Haig in the Varrock Museum.");
		talkToExaminer2 = new NpcStep(this, NpcID.EXAMINER, new WorldPoint(3362, 3337, 0), "Return to an Examiner in the Exam Centre south east of Varrock.", sealedLetter);

		searchBush = new ObjectStep(this, ObjectID.BUSH_2358, new WorldPoint(3357, 3372, 0), "Search the bushes north of the Exam Centre for a teddy.");
		takeTray = new DetailedQuestStep(this, new WorldPoint(3369, 3378, 0), "Pick up the tray in the south east of the dig site.", panningTray);
		talkToGuide = new NpcStep(this, NpcID.PANNING_GUIDE, new WorldPoint(3385, 3386, 0), "Talk to the Panning Guide nearby.", tea);
		panWater = new ObjectStep(this, ObjectID.PANNING_POINT, new WorldPoint(3384, 3381, 0), "Pan in the river for a special cup.", panningTray);
		pickpocketWorkmen = new NpcStep(this, NpcID.DIGSITE_WORKMAN, new WorldPoint(3372, 3390, 0), "Pickpocket workmen until you get an animal skull and a specimen brush.", true);
		((NpcStep) (pickpocketWorkmen)).addAlternateNpcs(NpcID.DIGSITE_WORKMAN_3630, NpcID.DIGSITE_WORKMAN_3631);
		((NpcStep) (pickpocketWorkmen)).setMaxRoamRange(100);
		talkToFemaleStudent = new NpcStep(this, NpcID.STUDENT_3634, new WorldPoint(3345, 3425, 0), "Talk to the female student in the north west of the Digsite twice.", teddybear);
		talkToOrangeStudent = new NpcStep(this, NpcID.STUDENT_3633, new WorldPoint(3369, 3419, 0), "Talk to the student in an orange shirt in the north east of the Digsite twice.", specialCup);
		talkToGreenStudent = new NpcStep(this, NpcID.STUDENT, new WorldPoint(3362, 3398, 0), "Talk to the student in a green shirt in the south of the Digsite twice.", skull);
		talkToFemaleStudent2 = new NpcStep(this, NpcID.STUDENT_3634, new WorldPoint(3345, 3425, 0), "Talk to the female student in the north west of the Digsite.");
		talkToOrangeStudent2 = new NpcStep(this, NpcID.STUDENT_3633, new WorldPoint(3369, 3419, 0), "Talk to the student in an orange shirt in the north east of the Digsite.");
		talkToGreenStudent2 = new NpcStep(this, NpcID.STUDENT, new WorldPoint(3362, 3398, 0), "Talk to the student in a green shirt in the south of the Digsite.");
		talkToFemaleStudent.addSubSteps(talkToFemaleStudent2);
		talkToOrangeStudent.addSubSteps(talkToOrangeStudent2);
		talkToGreenStudent.addSubSteps(talkToGreenStudent2);
		takeTest1 = new NpcStep(this, NpcID.EXAMINER, new WorldPoint(3362, 3337, 0), "Talk to an Examiner in the Exam Centre to take the first test.");
		takeTest1.addDialogSteps("Yes, I certainly am.", "The study of the earth, its contents and history.",
			"All that have passed the appropriate Earth Sciences exam.",
			"Gloves and boots to be worn at all times; proper tools must be used.");

		talkToFemaleStudent3 = new NpcStep(this, NpcID.STUDENT_3634, new WorldPoint(3345, 3425, 0), "Talk to the female student in the north west of the Digsite.");
		talkToOrangeStudent3 = new NpcStep(this, NpcID.STUDENT_3633, new WorldPoint(3369, 3419, 0), "Talk to the student in an orange shirt in the north east of the Digsite.");
		talkToGreenStudent3 = new NpcStep(this, NpcID.STUDENT, new WorldPoint(3362, 3398, 0), "Talk to the student in a green shirt in the south of the Digsite.");
		takeTest2 = new NpcStep(this, NpcID.EXAMINER, new WorldPoint(3362, 3337, 0), "Talk to an Examiner in the Exam Centre to take the second test.");
		takeTest2.addDialogSteps("I am ready for the next exam.", "Samples taken in rough form; kept only in sealed containers.",
			"Finds must be carefully handled, and gloves worn.", "Always handle with care; strike cleanly on its cleaving point.");

		talkToFemaleStudent4 = new NpcStep(this, NpcID.STUDENT_3634, new WorldPoint(3345, 3425, 0), "Talk to the female student in the north west of the Digsite.", opal);
		talkToFemaleStudent5 = new NpcStep(this, NpcID.STUDENT_3634, new WorldPoint(3345, 3425, 0), "Talk to the female student again.", opal);
		talkToOrangeStudent4 = new NpcStep(this, NpcID.STUDENT_3633, new WorldPoint(3369, 3419, 0), "Talk to the student in an orange shirt in the north east of the Digsite.");
		talkToGreenStudent4 = new NpcStep(this, NpcID.STUDENT, new WorldPoint(3362, 3398, 0), "Talk to the student in a green shirt in the south of the Digsite.");
		takeTest3 = new NpcStep(this, NpcID.EXAMINER, new WorldPoint(3362, 3337, 0), "Talk to an Examiner in the Exam Centre to take the third test.");
		takeTest3.addDialogSteps("I am ready for the last exam...", "Samples cleaned, and carried only in specimen jars.",
			"Brush carefully and slowly using short strokes.", "Handle bones very carefully and keep them away from other samples.");

		getJar = new ObjectStep(this, ObjectID.CUPBOARD_17302, new WorldPoint(3355, 3332, 0), "Search the cupboard on the south wall of the west room of the Exam Centre for a specimen jar.");
		((ObjectStep) (getJar)).addAlternateObjects(ObjectID.CUPBOARD_17303);
		getPick = new ObjectStep(this, ObjectID.CUPBOARD_17300, new WorldPoint(3356, 3337, 0), "Search the cupboard on the north wall of the west room of the Exam Centre for a rock pick.");
		((ObjectStep) (getPick)).addAlternateObjects(ObjectID.CUPBOARD_17301);
		getBrush = new NpcStep(this, NpcID.DIGSITE_WORKMAN, new WorldPoint(3372, 3390, 0), "Pickpocket workmen until you get a specimen brush.", true);
		((NpcStep) (getBrush)).addAlternateNpcs(NpcID.DIGSITE_WORKMAN_3630, NpcID.DIGSITE_WORKMAN_3631);
		((NpcStep) (getBrush)).setMaxRoamRange(100);
		digForTalisman = new ObjectStep(this, ObjectID.SOIL_2377, new WorldPoint(3374, 3438, 0), "Dig in the north east dig spot in the Digsite until you get a talisman.", trowelHighlighted, leatherBoots, leatherGloves);
		digForTalisman.addIcon(ItemID.TROWEL);
		talkToExpert = new NpcStep(this, NpcID.ARCHAEOLOGICAL_EXPERT, new WorldPoint(3357, 3334, 0), "Talk Archaeological expert in the Exam Centre.", talisman);

		useInvitationOnWorkman = new NpcStep(this, NpcID.DIGSITE_WORKMAN, new WorldPoint(3360, 3415, 0), "Use the invitation on any workman.", true, invitation);
		useInvitationOnWorkman.addIcon(ItemID.INVITATION_LETTER);
		useInvitationOnWorkman.addDialogStep("I lost the letter you gave me.");
		((NpcStep) (useInvitationOnWorkman)).addAlternateNpcs(NpcID.DIGSITE_WORKMAN_3630, NpcID.DIGSITE_WORKMAN_3631);
		((NpcStep) (useInvitationOnWorkman)).setMaxRoamRange(100);
		useRopeOnWinch = new ObjectStep(this, ObjectID.WINCH_2350, new WorldPoint(3353, 3417, 0), "Use a rope on the west winch.", rope);
		useRopeOnWinch.addIcon(ItemID.ROPE);
		goDownWinch = new ObjectStep(this, ObjectID.WINCH_2350, new WorldPoint(3353, 3417, 0), "Climb down the west winch.");
		pickUpRoot = new ItemStep(this, "Pick up some arcenia root.", arcenia);
		searchBricks = new ObjectStep(this, ObjectID.BRICK_2362, new WorldPoint(3378, 9824, 0), "Search the bricks to the south.");

		goUpRope = new ObjectStep(this, ObjectID.ROPE_2353, new WorldPoint(3369, 9826, 0), "Climb back to the surface.");
		useRopeOnWinch2 = new ObjectStep(this, ObjectID.WINCH_2351, new WorldPoint(3370, 3429, 0), "Use a rope on the north east winch.", rope);
		useRopeOnWinch2.addIcon(ItemID.ROPE);
		goDownToDoug = new ObjectStep(this, ObjectID.WINCH_2351, new WorldPoint(3370, 3429, 0), "Climb down the north east winch.");
		talkToDoug = new NpcStep(this, NpcID.DOUG_DEEPING, new WorldPoint(3351, 9819, 0), "Talk to Doug Deeping.");
		talkToDoug.addDialogStep("How could I move a large pile of rocks?");
		goUpFromDoug = new ObjectStep(this, ObjectID.ROPE_2352, new WorldPoint(3352, 9816, 0), "Leave Doug's cave.");
		unlockChest = new ObjectStep(this, ObjectID.CHEST_2361, new WorldPoint(3374, 3378, 0), "Use the key on the chest in the tent in the south of the Digsite.", key);
		unlockChest.addIcon(ItemID.CHEST_KEY_709);
		searchChest = new ObjectStep(this, ObjectID.CHEST_2360, new WorldPoint(3374, 3378, 0), "Search the chest.");
		useTrowelOnBarrel = new ObjectStep(this, NullObjectID.NULL_2359, new WorldPoint(3364, 3378, 0), "Use a trowel on the barrel west of the chest's tent.", trowelHighlighted);
		useTrowelOnBarrel.addIcon(ItemID.TROWEL);
		useVialOnBarrel = new ObjectStep(this, NullObjectID.NULL_2359, new WorldPoint(3364, 3378, 0), "Use a vial on the barrel west of the chest's tent.", vialHighlighted);
		useVialOnBarrel.addIcon(ItemID.VIAL);
		grindCharcoal = new DetailedQuestStep(this, "Grind charcoal with a pestle and mortar.", pestleAndMortar, charcoal);
		usePowderOnExpert = new NpcStep(this, NpcID.ARCHAEOLOGICAL_EXPERT, new WorldPoint(3357, 3334, 0), "Use the powder on the Archaeological expert in the Exam Centre.", powder);
		usePowderOnExpert.addIcon(ItemID.CHEMICAL_POWDER);
		useLiquidOnExpert = new NpcStep(this, NpcID.ARCHAEOLOGICAL_EXPERT, new WorldPoint(3357, 3334, 0), "Use the liquid on the Archaeological expert in the Exam Centre.", liquid);
		useLiquidOnExpert.addIcon(ItemID.UNIDENTIFIED_LIQUID);
		mixNitroWithNitrate = new DetailedQuestStep(this, "Mix the nitroglycerin and ammonium nitrate together.", nitro, nitrate);
		addCharcoal = new DetailedQuestStep(this, "Add charcoal to the vial.", groundCharcoal, mixedChemicals);
		addRoot = new DetailedQuestStep(this, "Add arcenia root to the vial.", arcenia, mixedChemicals2);
		goDownToExplode = new ObjectStep(this, ObjectID.WINCH_2350, new WorldPoint(3353, 3417, 0), "Climb down the rope on the west winch.", chemicalCompound, tinderbox);
		goDownToExplode2 = new ObjectStep(this, ObjectID.WINCH_2350, new WorldPoint(3353, 3417, 0), "Climb down the rope on the west winch.", tinderbox);
		goDownToExplode.addSubSteps(goDownToExplode2);
		useCompound = new ObjectStep(this, ObjectID.BRICK_2362, new WorldPoint(3378, 9824, 0), "Use the compound on the bricks to the south.", chemicalCompoundHighlighted);
		useCompound.addIcon(ItemID.CHEMICAL_COMPOUND);

		useTinderbox = new ObjectStep(this, ObjectID.BRICK_2362, new WorldPoint(3378, 9824, 0), "Use a tinderbox on the bricks to the south.", tinderboxHighlighted);
		useTinderbox.addIcon(ItemID.TINDERBOX);
		takeTablet = new ObjectStep(this, NullObjectID.NULL_17369, new WorldPoint(3373, 9746, 0), "Take the stone tablet in the south room.");
		goDownForTablet = new ObjectStep(this, ObjectID.WINCH_2350, new WorldPoint(3353, 3417, 0), "Climb down the rope on the west winch.");
		takeTablet.addSubSteps(goDownForTablet);

		goUpWithTablet = new ObjectStep(this, ObjectID.ROPE_2353, new WorldPoint(3369, 9762, 0), "Use the tablet on the Archaeological expert in the Exam Centre to complete the quest.", tablet);
		useTabletOnExpert = new NpcStep(this, NpcID.ARCHAEOLOGICAL_EXPERT, new WorldPoint(3357, 3334, 0), "Use the tablet on the Archaeological expert in the Exam Centre to complete the quest.", tablet);
		useTabletOnExpert.addIcon(ItemID.STONE_TABLET);
		useTabletOnExpert.addSubSteps(goUpWithTablet);

		syncStep = new DetailedQuestStep(this, "Open the quest's journal to sync your current quest state.");
	}

	@Override
	public ArrayList<String> getNotes()
	{
		return new ArrayList<>(Collections.singletonList("This quest helper is susceptible to getting out of sync with the actual quest. If this happens to you, opening up the quest's journal should fix it."));
	}

	@Override
	public ArrayList<ItemRequirement> getItemRequirements()
	{
		return new ArrayList<>(Arrays.asList(pestleAndMortar, vialHighlighted, tinderbox, tea, ropes2, opal, charcoal, leatherBoots, leatherGloves));
	}

	@Override
	public ArrayList<ItemRequirement> getItemRecommended()
	{
		return new ArrayList<>();
	}

	@Override
	public ArrayList<PanelDetails> getPanels()
	{
		ArrayList<PanelDetails> allSteps = new ArrayList<>();
		allSteps.add(new PanelDetails("Starting off", new ArrayList<>(Arrays.asList(talkToExaminer, talkToHaig, talkToExaminer2, searchBush, takeTray, talkToGuide, panWater, pickpocketWorkmen, talkToFemaleStudent, talkToFemaleStudent2,
			talkToOrangeStudent, talkToOrangeStudent2, talkToGreenStudent, talkToGreenStudent2, takeTest1))));

		allSteps.add(new PanelDetails("Exam 2", new ArrayList<>(Arrays.asList(talkToFemaleStudent3, talkToOrangeStudent3, talkToGreenStudent3, takeTest2))));
		allSteps.add(new PanelDetails("Exam 3", new ArrayList<>(Arrays.asList(talkToFemaleStudent4, talkToFemaleStudent5, talkToOrangeStudent4,
			talkToGreenStudent4, takeTest3)), opal));
		allSteps.add(new PanelDetails("Discovery", new ArrayList<>(Arrays.asList(getJar, getPick, digForTalisman, talkToExpert, useInvitationOnWorkman)), trowel, specimenBrush, leatherBoots, leatherGloves));
		allSteps.add(new PanelDetails("Digging deeper", new ArrayList<>(Arrays.asList(useRopeOnWinch, goDownWinch, pickUpRoot, searchBricks, goUpRope, useRopeOnWinch2, goDownToDoug,
			talkToDoug, goUpFromDoug, unlockChest, searchChest, useTrowelOnBarrel, useVialOnBarrel, useLiquidOnExpert, usePowderOnExpert, mixNitroWithNitrate, grindCharcoal, addCharcoal, addRoot, goDownToExplode,
			useCompound, useTinderbox, takeTablet, useTabletOnExpert)), ropes2, pestleAndMortar, vialHighlighted, tinderboxHighlighted, charcoal));

		return allSteps;
	}
}