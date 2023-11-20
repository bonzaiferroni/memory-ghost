# Quest App Design

## Introduction

I want to create a personal quest manager application that allows you to define overarching goals and then create quests within those goals. 

Each quest can: 
-have a flexible or specific target completion date.
-be set to repeat
-have prerequisite quests that must be completed first or unlock 

Quests are displayed in linear structures that flow from left to right. On the left are quests that must be completed before those further up the stream to the right.

Typically, the user does not choose a quest, an algorithm chooses among those that are available, perhaps based on some feedback like how connected they feel toward the overarching goal. 

There can also be an AI supported feature where quests are generated based on the goals expressed in dialog with the AI. The AI gathers information about where they are and creates suggestions for what quests they could follow next. 

## Story arc example

Let's use this software project as a framework for informing the product it describes.

Story arc: Mobile app developer
Next milestone: Create quest app

Now I can brainstorm quests that must be followed in this story arc. Brainstorming at this stage can be difficult. 

I could create quests like "marketing" or "testing" but it would quickly feel overwhelming, since those seem complicated on their own terms and the product is still fuzzy. 

One quest that is more appropriate for this stage is the app design, which I am doing right now: Create a design document draft.

## Data structures

Let's use the class Story to define our story arcs. I could define several for my life right now:

Story arcs:
-Mobile app developer
-Community website developer
-Freelance developer
-Uncle, son, brother, friend

Let's look at how these data structures could fit together
```
data class Story {
	val id: Int,
	val name: String,
}

data class Quest {
	val id: Int,
	val name: String,
	val description: String,
	val passCondition: String,
	val due: Instant?,
	val repeat: Boolean,
	val repeatInterval: Duration,
	val duration: Duration,
	val nextQuestId : Int,
	val avoidance: Float,
	val curiosity: Float,
	val confidence: Float,
	val sheetId: Int,
}

data class Sheet {
	val id: Int,
	val name: Int,
}

data class Variable {
	val id: Int,
	val type: VariableType,
	val name: String,
}

data class Column {
	val id: Int,
	val sheetId: Int,
	val variableId; Int,
	val order: Int,
}

data class Row {
	val id: Int,
	val sheetId; Int,
	val time: Instant,
}

data class Cell {
	val rowId: Int,
	val columnId: Int,
	val sheetId: Int,
	val value: String,
}

data class Schedule {
	val id: Int,
	val start: Instant,
	val end: Instant,
	val questId: Int,
}
```

