# HOTD Dragons: Caraxes Prototype v0.1

This is the first developer prototype of a Minecraft Java 1.20.1 fan addon designed around Saint's Dragons.

## Goal of v0.1

Get **Caraxes** into the game as a separate entity with:

- its own registry id: `hotddragons:caraxes`
- its own spawn egg
- its own original GeckoLib geometry
- its own original texture
- its own animation file
- Saint's Dragons' existing riding / flight / combat machinery as the temporary gameplay base

This milestone is intentionally a prototype. Caraxes currently extends Saint's Dragons' `Ignivorus`
class so we can validate the full spawn -> mount -> fly -> fight pipeline before replacing behavior one
system at a time.

## Requirements

- Minecraft Java 1.20.1
- Java 17
- Forge 47.4.10 or newer compatible 47.x
- Saint's Dragons 0.9.76+
- GeckoLib 4.8.4+

## Development dependencies

The Gradle project points at:
- Forge `1.20.1-47.4.10`
- CurseMaven Saint's Dragons file `8953089`
- GeckoLib Forge `1.20.1:4.8.4`

## Test commands

Once the mod is built and loaded:

```mcfunction
/summon hotddragons:caraxes
```

or

```mcfunction
/give @p hotddragons:caraxes_spawn_egg
```

## Important prototype limitations

- Flight, AI, attacks, taming and most sounds still come from the inherited Ignivorus logic.
- The model is a first-pass blockout, not the final Caraxes sculpt.
- Several inherited animation states are mapped to simple placeholder motions so no protected Saint's Dragons animation data is included.
- Hitbox, rider seat placement and camera tuning still need in-game testing.
- This repository contains no Saint's Dragons models, textures, animations or sound files.

## Next milestones

1. Verify the project compiles against the released Saint's Dragons API.
2. Spawn test and correct any registry/mod-id mismatch.
3. Tune Caraxes scale, hitbox, rider seat and camera.
4. Replace prototype geometry with the detailed serpentine model.
5. Add custom Caraxes flight handling: sharper banking, long-body sway, fast turning.
6. Add long-range narrow fire breath.
7. Add a custom bond system.
8. Add unique Caraxes stats and combat attacks.
9. Add original sounds.
10. Multiplayer/server test.

See `DESIGN_CARAXES.md` for the visual/gameplay target.
