# HOTD Dragons — Caraxes v0.2

Experimental Forge 1.20.1 addon built on top of Saint's Dragons 0.9.76.

## What changed from v0.1

- Rider seat is now positioned by the HOTD addon instead of inheriting Ignivorus' much taller seat geometry.
- Caraxes now has persistent addon-owned growth.
- New Caraxes begin at 35% visual scale and grow continuously to full adult size.
- Growth takes 10 real minutes in this prototype.
- Collision size changes through four growth stages: hatchling, juvenile, subadult and adult.
- Existing Caraxes saved by v0.1 are treated as adults so updating does not unexpectedly shrink them.
- Temporary QA shortcut: **sneak + right click Caraxes with blaze powder** advances growth by one minute.

## Dependencies

- Minecraft 1.20.1
- Forge 47.4.10+
- Saint's Dragons 0.9.76+
- GeckoLib 4.8.4+

## v0.2 test checklist

1. Spawn a brand-new Caraxes. It should be noticeably smaller than the v0.1 adult.
2. Mount it. The player should now sit close to the shoulder/base-of-neck area instead of floating far above it.
3. Fly and land while mounted and watch for seat jitter or clipping.
4. Sneak and right-click Caraxes with blaze powder several times. It should visibly increase in size.
5. Leave and re-enter the world. Growth progress should be preserved.
6. Let one reach adulthood and confirm its final size matches the old v0.1 model scale.

## Known v0.2 limitations

Caraxes still temporarily inherits Ignivorus' flight and combat machinery. That means Ignivorus-specific attacks such as the ground/rock ability can still appear. Those are scheduled to be replaced by Caraxes-specific fire, bite and aerial attacks after rider geometry and growth are stable.

The current dragon mesh is still a technical blockout. The detailed Caraxes model comes after this systems pass.
