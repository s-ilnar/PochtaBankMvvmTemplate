package ru.pochtabank.templates.activityTest

import com.android.tools.idea.wizard.template.Language
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.android.tools.idea.wizard.template.impl.activities.common.addMaterial3Dependency
import com.android.tools.idea.wizard.template.impl.activities.common.generateManifest
import com.android.tools.idea.wizard.template.impl.activities.common.generateSimpleLayout
import com.android.tools.idea.wizard.template.impl.activities.emptyActivity.src.emptyActivityJava
import com.android.tools.idea.wizard.template.impl.activities.emptyActivity.src.emptyActivityKt
import ru.pochtabank.templates.newModuleModule.emptyActivityKt

fun RecipeExecutor.generateEmptyActivity(
    moduleData: ModuleTemplateData,
    activityClass: String,
    generateLayout: Boolean,
    layoutName: String,
    isLauncher: Boolean,
    packageName: PackageName
) {
    val (projectData, srcOut) = moduleData
    val useAndroidX = projectData.androidXSupport
    val ktOrJavaExt = projectData.language.extension

//ui libs
    addDependency("libraries.supportCardView")
    addDependency("libraries.supportDesign")
    addDependency("libraries.supportConstraintLayout")
    addDependency("libraries.supportAppCompat")
    addDependency("libraries.recyclerview")

    //other
    addDependency("libraries.coreKtx")

    generateManifest(
        moduleData,
        activityClass,
        packageName,
        false,
        false,
        generateActivityTitle = false
    )


    val simpleActivity = emptyActivityKt(packageName, moduleData.name, activityClass, layoutName, generateLayout, useAndroidX)

    val simpleActivityPath = srcOut.resolve("$activityClass.$ktOrJavaExt")
    save(simpleActivity, simpleActivityPath)
    open(simpleActivityPath)
}
