package net.darkhax.infoaccessories;

import net.darkhax.bookshelf.lib.LoggingHelper;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "simplyarrows", name = "Simply Arrows", version = "@VERSION@", dependencies = "required-after:bookshelf@[2.2.462,);", certificateFingerprint = "@FINGERPRINT@")
public class SimplyArrows {

    public static final LoggingHelper LOG = new LoggingHelper("Simply Arrows");
    public static final RegistryHelper REGISTRY = new RegistryHelper("simplyarrows").enableAutoRegistration().setTab(CreativeTabs.MISC);

    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {

    }
}