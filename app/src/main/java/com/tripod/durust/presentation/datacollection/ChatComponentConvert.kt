package com.tripod.durust.presentation.datacollection

import com.tripod.durust.data.DateEntity

fun ChatComponentEntity.toChatComponent(): ChatComponent {
    return when (type) {
        ChatComponentType.Name -> ChatComponent.NameInputText(id, response = content)
        ChatComponentType.Gender -> ChatComponent.GenderInputUI(
            id,
            gender = GenderEntity.valueOf(content)
        )

        ChatComponentType.Weight -> ChatComponent.WeightInput(id, weight = content.toInt())
        ChatComponentType.Birthday -> {
            val dateParts = content.split("-").map { it.toInt() }
            ChatComponent.BirthdayInput(
                id,
                date = DateEntity(dateParts[0], dateParts[1], dateParts[2])
            )
        }

        ChatComponentType.Height -> ChatComponent.HeightInput(id, height = content)
        ChatComponentType.ExerciseFrequency -> ChatComponent.ExerciseFrequencyInput(
            id,
            frequency = content.toInt()
        )

        ChatComponentType.PreferredExercise -> ChatComponent.PreferredExerciseInput(
            id,
            exercise = ExerciseType.valueOf(content)
        )

        ChatComponentType.StepCount -> ChatComponent.StepCountInput(
            id,
            steps = StepsInputEntity.valueOf(content)
        )

        ChatComponentType.MealPreference -> ChatComponent.MealPreference(
            id,
            preference = MealPreferenceEntity.valueOf(content)
        )

        ChatComponentType.CalorieIntake -> ChatComponent.CalorieIntake(
            id,
            calories = content.toInt()
        )

        ChatComponentType.SleepSchedule -> ChatComponent.SleepSchedule(
            id,
            schedule = stringToWakeSleepEntity(content)
        )

        ChatComponentType.HealthIssues -> ChatComponent.HealthIssues(
            id,
            issues = HealthCondition.valueOf(content)
        )

        ChatComponentType.RoutineCheckUpFrequency -> ChatComponent.RoutineCheckUpFrequency(
            id,
            frequency = CheckUpFrequency.valueOf(content)
        )
    }
}

fun ChatComponent.toChatComponentEntity(): ChatComponentEntity {
    return when (this) {
        is ChatComponent.NameInputText -> ChatComponentEntity(
            id,
            ChatComponentType.Name,
            content = response
        )

        is ChatComponent.GenderInputUI -> ChatComponentEntity(
            id,
            ChatComponentType.Gender,
            gender.displayName
        )

        is ChatComponent.WeightInput -> ChatComponentEntity(
            id,
            ChatComponentType.Weight,
            weight.toString()
        )

        is ChatComponent.BirthdayInput -> ChatComponentEntity(
            id,
            ChatComponentType.Birthday,
            "${date.day}-${date.month}-${date.year}"
        )

        is ChatComponent.HeightInput -> ChatComponentEntity(id, ChatComponentType.Height, height)
        is ChatComponent.ExerciseFrequencyInput -> ChatComponentEntity(
            id,
            ChatComponentType.ExerciseFrequency,
            frequency.toString()
        )

        is ChatComponent.PreferredExerciseInput -> ChatComponentEntity(
            id,
            ChatComponentType.PreferredExercise,
            exercise.exerciseName
        )

        is ChatComponent.StepCountInput -> ChatComponentEntity(
            id,
            ChatComponentType.StepCount,
            steps.displayName
        )

        is ChatComponent.MealPreference -> ChatComponentEntity(
            id,
            ChatComponentType.MealPreference,
            preference.displayName
        )

        is ChatComponent.CalorieIntake -> ChatComponentEntity(
            id,
            ChatComponentType.CalorieIntake,
            calories.toString()
        )

        is ChatComponent.SleepSchedule -> ChatComponentEntity(
            id,
            ChatComponentType.SleepSchedule,
            wakeSleepEntityToString(schedule)
        )

        is ChatComponent.HealthIssues -> ChatComponentEntity(
            id,
            ChatComponentType.HealthIssues,
            issues.displayName
        )

        is ChatComponent.RoutineCheckUpFrequency -> ChatComponentEntity(
            id,
            ChatComponentType.RoutineCheckUpFrequency,
            frequency.displayName
        )
    }
}
