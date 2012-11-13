 package me.milesokeefe.smoothride; //Your package
     
import java.util.Hashtable;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
     
     
    public class SmoothRide extends JavaPlugin implements Listener {
    	Hashtable<UUID, Boolean> vehicles = new Hashtable<UUID, Boolean>();
        public FileConfiguration config; // declare config
    	public void onEnable() {
            getServer().getPluginManager().registerEvents(this, this);
            config = getConfig(); // get config
           // config.addDefault("boats_deploy_on_exit", true); // create our defaults
          //  config.addDefault("carts_deploy_on_exit", true); // create our defaults
           // config.addDefault("boats_break_lilypads", true); // create our defaults
            config.options().copyDefaults(true); // copy 'em
            saveConfig();
        }
    	//Logger log = Logger.getLogger("Minecraft");
        public void onDisable() {}
        
      @EventHandler
        public void onVehicleExit(VehicleExitEvent event){     
            if(event.getExited() instanceof Player){
            	final Vehicle ve = event.getVehicle();
            	final Entity veE = event.getVehicle();
            	Player player = (Player) event.getExited();

                if(veE.getClass().getName() == "org.bukkit.craftbukkit.entity.CraftBoat" && config.getBoolean("boats_deploy_on_exit")== true){
                	Boolean destroyed = false;
                	try{
                		if(vehicles.get(veE.getUniqueId()) != null){
                			destroyed = vehicles.get(veE.getUniqueId());
                		}
                	}catch(Exception e){
                		destroyed = false;
                	}
                	if(destroyed == false){
                    	ve.remove();
                    	PlayerInventory inventory = player.getInventory();
                    	ItemStack boatstack = new ItemStack(Material.BOAT, 1);
                    	inventory.addItem(boatstack);
                	}
                }else if (veE.getClass().getName() == "org.bukkit.craftbukkit.entity.CraftMinecart" && config.getBoolean("carts_deploy_on_exit")== true){
                	Boolean destroyed = false;
                	try{
                		if(vehicles.get(veE.getUniqueId()) != null){
                			destroyed = vehicles.get(veE.getUniqueId());
                		}
                	}catch(Exception e){
                		destroyed = false;
                	}
                	if(destroyed == false){
                    	ve.remove();
                    	PlayerInventory inventory = player.getInventory();
                    	ItemStack cartstack = new ItemStack(Material.MINECART, 1);
                    	inventory.addItem(cartstack);
                	}
                }
            	
            }
    }

       @EventHandler
        public void onVehicleDestroy(VehicleDestroyEvent event){
            
            if(event.getVehicle() instanceof Vehicle){
            	final Entity veE = event.getVehicle();
                if(veE.getClass().getName() == "org.bukkit.craftbukkit.entity.CraftBoat"){
                	vehicles.put(veE.getUniqueId(), true);
                }else if (veE.getClass().getName() == "org.bukkit.craftbukkit.entity.CraftMinecart"){
                	vehicles.put(veE.getUniqueId(), true);
                }
            	
            }
        }
        @EventHandler
        public void onVehicleEnter(VehicleEnterEvent event){
            
            if(event.getEntered() instanceof Vehicle){
            	final Entity veE = event.getVehicle();
                if(veE.getClass().getName() == "org.bukkit.craftbukkit.entity.CraftBoat"){
                	vehicles.put(veE.getUniqueId(), false);
                }else if (veE.getClass().getName() == "org.bukkit.craftbukkit.entity.CraftMinecart"){
                	vehicles.put(veE.getUniqueId(), false);
                }
            	
            }
        }
       /* @EventHandler
        public void onVehicleCreate(VehicleCreateEvent event){
        	Player player = Bukkit.getServer().getPlayer("milesokeefe");
        	event.getVehicle().setPassenger(player);
        	log.info("Vehicle created");
            /*if(event.getVehicle() instanceof Vehicle){
            	final Entity veE = event.getVehicle();
                if(veE instanceof Boat){
                	log.info("Boat created");
                	List<Entity> possibly_player = veE.getNearbyEntities(10.0, 10.0, 10.0);
                	for(int i =0; i< possibly_player.size();i++){
                		log.info("entity type" + i +": " +  possibly_player.get(i).getType());
                		if(possibly_player.get(i).getType()  == EntityType.PLAYER){
                			log.info("Boat created by " + ((Player) possibly_player.get(i)).getName());
                			veE.setPassenger((Entity)possibly_player.get(i));
                			break;
                		}
                	}
                	
                }else if (veE.getClass().getName() == "org.bukkit.craftbukkit.entity.CraftMinecart"){
                	
                }
            	
            }(
        }*/
        @EventHandler
        public void onVehicleBlockCollision(VehicleBlockCollisionEvent event){

        	if(event.getVehicle() instanceof Vehicle){
            	final Entity veE = event.getVehicle();
                if(veE.getClass().getName() == "org.bukkit.craftbukkit.entity.CraftBoat" && config.getBoolean("boats_break_lilypads")== true){
                	if(event.getBlock().getType() == Material.STATIONARY_WATER){
                		event.getBlock().breakNaturally();
                		event.getBlock().setType(Material.WATER);
                	}
                }else if (veE.getClass().getName() == "org.bukkit.craftbukkit.entity.CraftMinecart"){
                	
                }
            	
            }
        }
    }
