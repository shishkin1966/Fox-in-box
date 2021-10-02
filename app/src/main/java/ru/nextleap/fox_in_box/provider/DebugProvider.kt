package ru.nextleap.fox_in_box.provider

import com.facebook.stetho.Stetho
import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.provider.ApplicationProvider

class DebugProvider : AbsProvider(), IDebugProvider {
    companion object {
        const val NAME = "DebugProvider"
    }

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is IDebugProvider) 0 else 1
    }

    override fun onRegister() {
        //Stetho.initializeWithDefaults(ApplicationProvider.appContext)

        // Create an InitializerBuilder
        val initializerBuilder = Stetho.newInitializerBuilder(ApplicationProvider.appContext)

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
            Stetho.defaultInspectorModulesProvider(ApplicationProvider.appContext)
        )

        // Enable command line interface
        initializerBuilder.enableDumpapp(
            Stetho.defaultDumperPluginsProvider(ApplicationProvider.appContext)
        )

        // Use the InitializerBuilder to generate an Initializer
        val initializer = initializerBuilder.build()

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer)

    }
}