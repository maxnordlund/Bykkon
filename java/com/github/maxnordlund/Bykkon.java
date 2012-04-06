/**
 * Bykkon is a python integration for Bukkit
 * 
 * @version 07-04-2012
 * @author Max Nordlund
 *
 */
package com.github.maxnordlund;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.python.util.PythonInterpreter;

/**
 * The main class and entry point.
 * 
 * Loads all python scripts and executes them.
 * 
 * @version 07-04-2012
 * @author Max Nordlund
 *
 */
public class Bykkon extends JavaPlugin {

	private PythonInterpreter jython;
	private Logger log;

	/**
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable() {
		this.jython.cleanup();
	}

	/**
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		this.log = this.getLogger();
		this.jython = new PythonInterpreter();
		File folder = this.getDataFolder();
		if(folder.exists()) {
			this.deepExecute(folder.getAbsoluteFile());
		}
	}

	/**
	 * Does a depth first search of the given folder and executes all python scripts within it.
	 * 
	 * @param folder
	 */
	private void deepExecute(File folder) {
		for(File file: folder.listFiles()) {
			if(file.isDirectory()) {
				this.deepExecute(file);
			} else if(file.getName().endsWith(".py")) {
				try {
					this.jython.execfile(new FileInputStream(file));
				} catch (FileNotFoundException e) {
					this.log.severe("Couldn't find file " + file.getName());
				}
			} 
		}
	}

	/**
	 * @see org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// TODO: Add dynamic python execution
		return false;
	}

}
