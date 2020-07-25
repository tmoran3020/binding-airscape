# Airscape Binding

This binding integrates with [Airscape](https://airscapefans.com) whole house fans that have [2nd Gen Controls](https://blog.airscapefans.com/archives/gen-2-controls-api). If the optional Digital Monitoring Package [DPM](https://cdn.shopify.com/s/files/1/2256/6107/files/Archive_2ND_GEN_RMT_IOM_2015.pdf) is installed, it will also display variables such as outside and inside temperature.

## Supported Things

Any Airscape Whole House Fan equipped with Gen 2 controls. Note that gen2 controls are no longer a typical option and must be specifically requested at time of order.

## Discovery

Thing binding does not support discovery.

## Thing Configuration

This binding requires the IP address of the fan controller and, optionally, a polling interval. If not polling interval is selected, it will default to polling every 30 seconds.

## Channels

| channel            | type          | description                                                                 |
|--------------------|---------------|-----------------------------------------------------------------------------|
| fanControl         | Rollershutter | This channel controls the fan speed - up (increase speed), down, and stop.  |
| speed              | Number        | Speed of the fan, from 0 to 10. 0 is off, 10 is full speed.                 |
| timeRemaining      | Number        | Time remaining before the fan turns off automatically, in minutes.          |
| addOneHour         | Switch        | When turned ON, this channel will add one hour of time to timeRemaining     |
| atticTemperature   | Number        | Temperature of attic, in degrees F, if equipped.                            |
| insideTemperature  | Number        | Temperature inside house, in degrees F, if equipped.                        |
| outsideTemperature | Number        | Temperature outside house, in degrees F, if equipped.                       |
| cfm                | Number        | The volume of air moved by the fan, in cubic feet per minute.               |
| power              | Number        | The power used by the fan, in watts.                                        |
| model              | String        | The model number of the fan.                                                |
| softwareVersion    | String        | The software version of the fan.                                            |
| interlock1         | Contact       | If interlock 1 is active (open). Used by safety equipment to turn off fan   |
| interlock2         | Contact       | If interlock 2 is active (open). Used by safety equipment to turn off fan   |
| macAddess          | String        | The MAC address of the controller                                           |
| doorInProcess      | Contact       | Doors are in the process of opening (open). Does NOT refer to door state.   |

## Full Example

airscape.things:

```
Thing airscape:fan:whf [ ipAddress="192.168.1.2", pollingInterval="30" ]
```

airscape.items:

```
Number fanSpeed "Fan Speed" { channel="airscape:fan:whf:speed" }
Rollershutter fanControl "Fan" { channel="airscape:fan:whf:fanControl" }
Number fanTimeRemaining "Time Remaining" { channel="airscape:fan:whf:timeRemaining" }
Switch fanAddOneHour "Add One Hour" { channel="airscape:fan:whf:addOneHour" }
Number fanAtticTemperature "Attic Temperature" { channel="airscape:fan:whf:atticTemperature" }
Number fanInsideTemperature "Inside Temperature" { channel="airscape:fan:whf:insideTemperature" }
Number fanOutsideTemperature "Outside Temperature" { channel="airscape:fan:whf:outsideTemperature" }
Number fanCfm "CFM" { channel="airscape:fan:whf:cfm" }
Number fanPower "Power" { channel="airscape:fan:whf:power" }
String fanModel "Model" { channel="airscape:fan:whf:model" }
String fanSoftwareVersion "Software Version" { channel="airscape:fan:whf:softwareVersion" }
Contact fanInterlock1 "Interlock 1" { channel="airscape:fan:whf:interlock1" }
Contact fanInterlock2 "Interlocak 2" { channel="airscape:fan:whf:interlock2" }
String fanMacAddress "Mac Address" { channel="airscape:fan:whf:macAddress" }
Contact fanDoorInProcess "Door in process" { channel="airscape:fan:whf:doorInProcess" }
```

airscape.sitemap:

```
sitemap airscape label="Main Menu" {
    Frame label="Whole House Fan" {
		Text item=WHF_Fan_speed label="Fan speed" icon="fan"
		Default item=Fan_rollershutter label="Fan"
		Text item=timeRemaining label="Time Remaining"
		Switch item=addOneHour label="Add One Hour"
		Text item=atticTemperature label="Attic Temperature"
		Text item=insideTemperature label="Inside Temperature"
		Text item=outsideTemperature label="Outside Temperature"
		Text item=cfm label="CFM"
		Text item=power label="Power"
		Text item=model label="Model"
		Text item=interlock1 label="Interlock 1 [%s]"
		Text item=interlock2 label="Interlock 2 [%s]"
		Text item=macAddress label="Mac Address"
		Text item=doorInProcess label="Door in process [%s]"
	}
}
```

## Notes

* If system is not equipped with optional temperature sensors, it will return -99 for these values.
* The "rollershutter" type is a bit odd for a ... non-rollershutter. However the UP/DOWN/STOP actions almost perfectly correspond to how Airscape controls work.
* There is no "ON" function - from the off state, turn the speed UP. The reverse does not work though - if you go DOWN from 1, it will stay at speed 1 and not turn off the fan. This is how the API is implemented by Airscape.
