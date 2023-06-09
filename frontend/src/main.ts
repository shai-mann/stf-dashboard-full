import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

import {AppModule} from './app/app.module';

import '@cds/core/icon/register.js';
import {ClarityIcons, unknownStatusIcon, warningStandardIcon} from '@cds/core/icon';
import {WeatherIconShape} from "./app/components/weather-icon/weather-icon.component";


platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));

ClarityIcons.addIcons(
  unknownStatusIcon,
  warningStandardIcon
);
