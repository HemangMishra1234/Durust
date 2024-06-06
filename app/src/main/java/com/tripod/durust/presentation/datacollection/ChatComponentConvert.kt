package com.tripod.durust.presentation.datacollection

import com.tripod.durust.data.DateEntity

fun ChatComponentEntity.toChatComponent(): OnBoardingChatComponent {
    return when (type) {
        ChatComponentType.Name -> OnBoardingChatComponent.NameInputText(id, response = content)
        ChatComponentType.Gender -> OnBoardingChatComponent.GenderInputUI(
            id,
            gender = GenderEntity.valueOf(content)
        )

        ChatComponentType.Weight -> OnBoardingChatComponent.WeightInput(id, weight = content.toInt())
        ChatComponentType.Birthday -> {
            val dateParts = content.split("-").map { it.toInt() }
            OnBoardingChatComponent.BirthdayInput(
                id,
                date = DateEntity(dateParts[0], dateParts[1], dateParts[2])
            )
        }

        ChatComponentType.Height -> OnBoardingChatComponent.HeightInput(id, height = content)
        ChatComponentType.ExerciseFrequency -> OnBoardingChatComponent.ExerciseFrequencyInput(
            id,
            frequency = content.toInt()
        )

        ChatComponentType.PreferredExercise -> OnBoardingChatComponent.PreferredExerciseInput(
            id,
            exercise = ExerciseType.valueOf(content)
        )

        ChatComponentType.StepCount -> OnBoardingChatComponent.StepCountInput(
            id,
            steps = StepsInputEntity.valueOf(content)
        )

        ChatComponentType.MealPreference -> OnBoardingChatComponent.MealPreference(
            id,
            preference = MealPreferenceEntity.valueOf(content)
        )

        ChatComponentType.CalorieIntake -> OnBoardingChatComponent.CalorieIntake(
            id,
            calories = content.toInt()
        )

        ChatComponentType.SleepSchedule -> OnBoardingChatComponent.SleepSchedule(
            id,
            schedule = stringToWakeSleepEntity(content)
        )

        ChatComponentType.HealthIssues -> OnBoardingChatComponent.HealthIssues(
            id,
            issues = HealthCondition.valueOf(content)
        )

        ChatComponentType.RoutineCheckUpFrequency -> OnBoardingChatComponent.RoutineCheckUpFrequency(
            id,
            frequency = CheckUpFrequency.valueOf(content)
        )
    }
}

fun OnBoardingChatComponent.toChatComponentEntity(): ChatComponentEntity {
    return when (this) {
        is OnBoardingChatComponent.NameInputText -> ChatComponentEntity(
            id,
            ChatComponentType.Name,
            content = response
        )

        is OnBoardingChatComponent.GenderInputUI -> ChatComponentEntity(
            id,
            ChatComponentType.Gender,
            gender.displayName
        )

        is OnBoardingChatComponent.WeightInput -> ChatComponentEntity(
            id,
            ChatComponentType.Weight,
            weight.toString()
        )

        is OnBoardingChatComponent.BirthdayInput -> ChatComponentEntity(
            id,
            ChatComponentType.Birthday,
            "${date.day}-${date.month}-${date.year}"
        )

        is OnBoardingChatComponent.HeightInput -> ChatComponentEntity(id, ChatComponentType.Height, height)
        is OnBoardingChatComponent.ExerciseFrequencyInput -> ChatComponentEntity(
            id,
            ChatComponentType.ExerciseFrequency,
            frequency.toString()
        )

        is OnBoardingChatComponent.PreferredExerciseInput -> ChatComponentEntity(
            id,
            ChatComponentType.PreferredExercise,
            exercise.exerciseName
        )

        is OnBoardingChatComponent.StepCountInput -> ChatComponentEntity(
            id,
            ChatComponentType.StepCount,
            steps.displayName
        )

        is OnBoardingChatComponent.MealPreference -> ChatComponentEntity(
            id,
            ChatComponentType.MealPreference,
            preference.displayName
        )

        is OnBoardingChatComponent.CalorieIntake -> ChatComponentEntity(
            id,
            ChatComponentType.CalorieIntake,
            calories.toString()
        )

        is OnBoardingChatComponent.SleepSchedule -> ChatComponentEntity(
            id,
            ChatComponentType.SleepSchedule,
            wakeSleepEntityToString(schedule)
        )

        is OnBoardingChatComponent.HealthIssues -> ChatComponentEntity(
            id,
            ChatComponentType.HealthIssues,
            issues.displayName
        )

        is OnBoardingChatComponent.RoutineCheckUpFrequency -> ChatComponentEntity(
            id,
            ChatComponentType.RoutineCheckUpFrequency,
            frequency.displayName
        )
    }
}
