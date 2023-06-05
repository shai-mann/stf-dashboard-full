import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

import {AppModule} from './app/app.module';

import '@cds/core/icon/register.js';
import {ClarityIcons, unknownStatusIcon, warningStandardIcon} from '@cds/core/icon';
import {CLOUDY, PARTLY_CLOUDY, POURING, RAINY, SUNNY} from "./app/utils";


platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));

ClarityIcons.addIcons(
  unknownStatusIcon,
  warningStandardIcon,
  [SUNNY, '<img src="assets/images/weather/sunny.svg" alt="Sunny Weather">'],
  [PARTLY_CLOUDY, '<img src="assets/images/weather/partly-cloudy.svg" alt="Partly Cloudy Weather">'],
  [CLOUDY, '<img src="assets/images/weather/cloudy.svg" alt="Cloudy Weather">'],
  [RAINY, '<img src="assets/images/weather/rainy.svg" alt="Raining Weather">'],
  [POURING, '<img src="assets/images/weather/pouring.svg" alt="Pouring Weather">']
);
