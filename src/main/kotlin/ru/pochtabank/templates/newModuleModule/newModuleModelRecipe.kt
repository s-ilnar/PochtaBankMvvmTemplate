package ru.pochtabank.templates.newModuleModule

import com.android.tools.idea.wizard.template.RecipeExecutor
import ru.pochtabank.templates.models.BuildSettings

fun RecipeExecutor.newModuleRecipe(
    buildSettings: BuildSettings,
) {
    val (projectData, srcOut) = buildSettings.moduleData
    val useAndroidX = projectData.androidXSupport
    val ktOrJavaExt = projectData.language.extension


    //ui libs
    addDependency("libraries.supportCardView")
    addDependency("libraries.supportDesign")
    addDependency("libraries.supportConstraintLayout")
    addDependency("libraries.supportAppCompat")
    addDependency("libraries.recyclerview")

    //core ui and utils
    addDependency("project(':coreui')")
    addDependency("project(':core')")
    addDependency("project(':utils')")

    //koin di
    addDependency("libraries.koinCore")
    addDependency("libraries.koinAndroid")

    //arch libs
    addDependency("libraries.livedata")

    //rx java for api
    addDependency("libraries.rxJava2")
    addDependency("libraries.rxAndroid2")

    //for api layer
    addDependency("libraries.retrofit")
    addDependency("project(':api')")

    //image utils
    addDependency("libraries.glide")
    addDependency("libraries.glideOkHttp")

    //testing
    addDependency("libraries.testingJunit", "testCompile")
    addDependency("libraries.testingMockito", "testCompile")
    addDependency("libraries.testingMockitoInline", "testCompile")
    addDependency("libraries.testingAndroidx", "testCompile")
    addDependency("project(':test-utils')", "testCompile")

    //other
    addDependency("libraries.coreKtx")

    //addMaterial3Dependency()
//    generateManifest(
//        moduleData = buildSettings.moduleData,
//        "Ataaaa",
//        packageName = buildSettings.packageName,
//        isLauncher = false,
//        hasNoActionBar = false,
//        generateActivityTitle = false
//    )

    createDirectory(srcOut.resolve("data"))
    createDirectory(srcOut.resolve("di"))
    createDirectory(srcOut.resolve("domain"))
    createDirectory(srcOut.resolve("presentation/ui"))

    val simpleActivity = emptyActivityKt(
        buildSettings.packageName,
        buildSettings.moduleData.name,
        "ActivityClassG",
        "layoutName",
        false,
        useAndroidX
    )

    val simpleActivityPath = srcOut.resolve("ActivityClassG.kt")
    save(simpleActivity, simpleActivityPath)
    open(simpleActivityPath)
}