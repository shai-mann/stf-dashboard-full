import {Component, Input} from '@angular/core';
import {ClarityIcons} from "@cds/core/icon";

/**
 * The icon shapes for each given threshold.
 */
export enum WeatherIconShape {
  SUNNY = "weather-sunny",
  PARTLY_CLOUDY = "weather-partly-cloudy",
  CLOUDY = "weather-cloudy",
  RAINY = "weather-rainy",
  POURING = "weather-pouring"
}

ClarityIcons.addIcons(
  [WeatherIconShape.SUNNY, '<img src="assets/images/weather/sunny.svg" alt="Sunny Weather">'],
  [WeatherIconShape.PARTLY_CLOUDY, '<img src="assets/images/weather/partly-cloudy.svg" alt="Partly Cloudy Weather">'],
  [WeatherIconShape.CLOUDY, '<img src="assets/images/weather/cloudy.svg" alt="Cloudy Weather">'],
  [WeatherIconShape.RAINY, '<img src="assets/images/weather/rainy.svg" alt="Raining Weather">'],
  [WeatherIconShape.POURING, '<img src="assets/images/weather/pouring.svg" alt="Pouring Weather">']
);

@Component({
  selector: 'weather-icon',
  templateUrl: './weather-icon.component.html',
  styleUrls: ['./weather-icon.component.scss']
})
export class WeatherIconComponent {

  @Input()
  state: WeatherIconShape;

}
