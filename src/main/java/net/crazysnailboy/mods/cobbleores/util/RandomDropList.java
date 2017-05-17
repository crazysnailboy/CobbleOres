package net.crazysnailboy.mods.cobbleores.util;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RandomDropList
{

	private final NavigableMap<Double, ItemStack> map = new TreeMap<Double, ItemStack>();
	private final Random random;
	private double total = 0;


	public RandomDropList()
	{
		this.random = new Random();
	}


	public void buildDropsList()
	{
		this.map.clear();

		String fileContents = FileUtils.readFileContents("drops.json");
		JsonArray jsonArray = new JsonParser().parse(fileContents).getAsJsonArray();

		for (JsonElement jsonElement : jsonArray)
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();

			String name = jsonObject.get("name").getAsString();
			int meta = (jsonObject.has("meta") ? jsonObject.get("meta").getAsInt() : 0);
			int weight = jsonObject.get("weight").getAsInt();

			ItemStack stack = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(name)), 1, meta);
			if (weight > 0)
			{
				total += weight;
				map.put(total, stack);
			}
		}
	}

	public ItemStack getRandomDrop()
	{
		double value = random.nextDouble() * total;
		return map.ceilingEntry(value).getValue().copy();
	}

}
