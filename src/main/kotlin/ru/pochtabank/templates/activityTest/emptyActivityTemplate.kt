package ru.pochtabank.templates.activityTest

import com.android.tools.idea.wizard.template.BooleanParameter
import com.android.tools.idea.wizard.template.Category
import com.android.tools.idea.wizard.template.CheckBoxWidget
import com.android.tools.idea.wizard.template.Constraint.CLASS
import com.android.tools.idea.wizard.template.Constraint.LAYOUT
import com.android.tools.idea.wizard.template.Constraint.NONEMPTY
import com.android.tools.idea.wizard.template.Constraint.UNIQUE
import com.android.tools.idea.wizard.template.FormFactor
import com.android.tools.idea.wizard.template.LanguageWidget
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageNameWidget
import com.android.tools.idea.wizard.template.StringParameter
import com.android.tools.idea.wizard.template.TemplateConstraint
import com.android.tools.idea.wizard.template.TextFieldWidget
import com.android.tools.idea.wizard.template.WizardUiContext
import com.android.tools.idea.wizard.template.activityToLayout
import com.android.tools.idea.wizard.template.booleanParameter
import com.android.tools.idea.wizard.template.impl.activities.common.MIN_API
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter
import com.android.tools.idea.wizard.template.layoutToActivity
import com.android.tools.idea.wizard.template.stringParameter
import com.android.tools.idea.wizard.template.template
import java.io.File

val emptyActivityTemplate get() = template {
    name = "Empty Views Activity 2222"
    minApi = MIN_API
    description = "Creates a new empty activity 22223"

    category = Category.Activity
    formFactor = FormFactor.Mobile
    screens = listOf(WizardUiContext.ActivityGallery, WizardUiContext.MenuEntry, WizardUiContext.NewProject, WizardUiContext.NewModule)
    constraints = listOf(TemplateConstraint.AndroidX, TemplateConstraint.Material3)

    val generateLayout: BooleanParameter = booleanParameter {
        name = "Generate a Layout File"
        default = true
        help = "If true, a layout file will be generated"
    }
    lateinit var layoutName: StringParameter
    val activityClass: StringParameter = stringParameter {
        name = "Activity Name"
        constraints = listOf(CLASS, UNIQUE, NONEMPTY)
        suggest = {
            layoutToActivity(layoutName.value)
        }
        default = "MainActivity"
        help = "The name of the activity class to create"
    }
    layoutName = stringParameter {
        name = "Layout Name"
        constraints = listOf(LAYOUT, UNIQUE, NONEMPTY)
        suggest = {
            activityToLayout(activityClass.value)
        }
        default = "activity_main"
        visible = { generateLayout.value }
        help = "The name of the UI layout to create for the activity"
    }
    val isLauncher: BooleanParameter = booleanParameter {
        name = "Launcher Activity"
        visible = { !isNewModule }
        default = false
        help = "If true, this activity will have a CATEGORY_LAUNCHER intent filter, making it visible in the launcher"
    }
    val packageName = defaultPackageNameParameter

    widgets(
        TextFieldWidget(activityClass),
        CheckBoxWidget(generateLayout),
        TextFieldWidget(layoutName),
        CheckBoxWidget(isLauncher),
        PackageNameWidget(packageName),
        LanguageWidget()
    )

    recipe = { data ->
        generateEmptyActivity(
            data as ModuleTemplateData, activityClass.value, generateLayout.value, layoutName.value, isLauncher.value, packageName.value
        )
    }
}