import {defineConfig, devices} from '@playwright/test';
import * as dotenv from 'dotenv'; dotenv.config({path: '../.env'});

export default defineConfig({
    reporter: [['html', {open: 'never'}]],
    use:{
        baseURL: process.env.BASE_URL || 'https://the-internet.herokuapp.com',
        trace: 'on', // capture on first run
        video: 'on', // capture on first run
        screenshot: 'on' // capture on first run
    },
    testDir: './tests',
    outputDir: './test-artifacts/',
    projects:[{name: 'chromium', use: {...devices['Desktop Chrome']}}],
    timeout: 30 * 1000,
    expect: {timeout: 5000}
});