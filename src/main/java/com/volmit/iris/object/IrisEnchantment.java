package com.volmit.iris.object;

import java.lang.reflect.Field;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

import com.volmit.iris.Iris;
import com.volmit.iris.util.Desc;
import com.volmit.iris.util.DontObfuscate;
import com.volmit.iris.util.MaxNumber;
import com.volmit.iris.util.MinNumber;
import com.volmit.iris.util.RNG;
import com.volmit.iris.util.Required;

import lombok.Data;

@Desc("Represents an enchantment & level")
@Data
public class IrisEnchantment
{
	@Required
	@DontObfuscate
	@Desc("The enchantment")
	private String enchantment = "";

	@MinNumber(1)
	@DontObfuscate
	@Desc("Minimum amount of this loot")
	private int minLevel = 1;

	@MinNumber(1)
	@DontObfuscate
	@Desc("Maximum amount of this loot")
	private int maxLevel = 1;

	@MinNumber(0)
	@MaxNumber(1)
	@DontObfuscate
	@Desc("The chance that this enchantment is applied (0 to 1)")
	private double chance = 1;

	public IrisEnchantment()
	{

	}

	public void apply(RNG rng, ItemMeta meta)
	{
		if(rng.nextDouble() < chance)
		{
			meta.addEnchant(getEnchant(), getLevel(rng), true);
		}
	}

	public Enchantment getEnchant()
	{
		for(Field i : Enchantment.class.getDeclaredFields())
		{
			if(i.getType().equals(Enchantment.class) && i.getName().equals(getEnchantment()))
			{
				try
				{
					return (Enchantment) i.get(null);
				}

				catch(IllegalArgumentException | IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}

		Iris.warn("Can't find enchantment type: " + getEnchantment());

		return null;
	}

	public int getLevel(RNG rng)
	{
		return rng.i(getMinLevel(), getMaxLevel());
	}
}