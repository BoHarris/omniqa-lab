import {test} from '@playwright/test';
import {LoginPage} from './LoginPage';

test('Login flow', async ({page}) => {
    const login = new LoginPage(page);
    await login.goto();
    await login.login('tomsmith', 'SuperSecretPassword!');
    await login.assertLoginSuccess();
    await login.logout();
    await login.goto();
    await login.login('tomsmith', 'invalid'); // Invalid password
    await login.assertLoginFailure();
    await login.login('invalid', 'SuperSecretPassword!'); // Invalid username
    await login.assertLoginFailure();
});