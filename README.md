# FishingGenerator-ISetsAddon

This addon allows the player to earn armor fragments by fishing.

`Dynamic_Amount`: When this option is enabled, the system will use the fish type and amount specified in the `config.yml` file to determine how many fragments to provide.

To enable this generator, replace the fragment generator section of your set's file with the below.

```yaml
Fragment_Generator:
  Enabled: true
  Type: IGen
  Source: Fishing
  Amount_To_Give: 1
  Dynamic_Amount: true
  Chance: 100
  Physical: true
  Disabled_Worlds: []
  Give_Message: "&7You have received &fx{amount} &c&lFire Fragment"
```

Config:
```yaml
Custom_Amounts:
  - "Cod:1"
  - "Salmon:3"
  - "PufferFish:2"
  - "Tadpole:5"
  - "TropicalFish:10"
```
