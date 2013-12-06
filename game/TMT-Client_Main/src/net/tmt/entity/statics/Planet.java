package net.tmt.entity.statics;

import net.tmt.entity.Entity2D;
import net.tmt.entity.statics.area.MissionAreaOffer;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.ZoomManager;
import net.tmt.gfx.Graphics;
import net.tmt.global.mission.MissionManager;
import net.tmt.global.mission.PlanetMission;
import net.tmt.map.Terrain;
import net.tmt.map.World;
import net.tmt.util.PlanetNameUtil;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class Planet extends Entity2D {
	private static int	currID		= 0;

	private String		name;

	private boolean		isOnScreen	= true;
	private int			planetId;
	private int			radius;
	private Terrain		baseTerrain;


	public Planet(final Vector2d pos, final Terrain baseTerrain, final int radius) {
		super(pos);
		planetId = currID++;
		this.radius = radius;
		this.baseTerrain = baseTerrain;
		name = PlanetNameUtil.getPlanetName(pos.hashCode() + RandomUtil.SEED);

		// FIXME: BUG (maybe not here): Planets far away sometimes doesn't have
		// OfferAreas
		MissionManager.getInstance()
				.registerArea(new MissionAreaOffer(getPos(), new PlanetMission(this), radius * 1.2));
	}

	public int getPlanetId() {
		return planetId;
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		super.update(caller, world, delta);

		// only render if its not to far away from screen
		double dist = world.getOffset().distanceTo(pos);
		isOnScreen = dist < ZoomManager.getWidthZoomed() * 2;
	}

	@Override
	public void render(final Graphics g) {
		if (!isOnScreen)
			return;

		switch (baseTerrain) {
		case PLANET_GRASS:
			g.setColor(Color.GREEN);
			break;
		case PLANET_WATER:
			g.setColor(Color.BLUE);
			break;
		case PLANET_DESERT:
			g.setColor(Color.YELLOW);
			break;
		case PLANET_SNOW:
			g.setColor(Color.WHITE);
			break;
		case PLANET_FOREST:
			g.setColor(Color.DKGREY);
			break;
		case PLANET_SWAMP:
			g.setColor(Color.PURPLE);
			break;
		case PLANET_MOUNTAIN:
			g.setColor(Color.ORANGE);
			break;
		default:
			break;
		}
		g.drawSprite(pos, baseTerrain.getSprite());
		g.drawCircle(pos.x, pos.y, radius);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(final int radius) {
		this.radius = radius;
	}

	public Terrain getBaseTerrain() {
		return baseTerrain;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return super.toString() + "[name= " + name + ", terrain=" + baseTerrain.name() + "]";
	}
}
