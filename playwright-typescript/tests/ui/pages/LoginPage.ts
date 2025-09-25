import {Locator, Page, expect} from '@playwright/test';

export class LoginPage {
    readonly page: Page;
    readonly username: Locator;
    readonly password: Locator;
    readonly loginButton: Locator;
    readonly flashMessage: Locator;
    
    constructor(page: Page) {
        this.page = page;
        this.username = page.getByLabel('Username');
        this.password = page.getByLabel('Password');
        this.loginButton = page.getByRole('button', {name: 'Login'});
        this.flashMessage = page.locator('#flash');
    }

    async goto(){
        await this.page.goto('/login');
        await expect(this.username).toBeVisible();
        await expect(this.password).toBeVisible();
    }

    async login(username: string, password: string){
        await this.username.fill(username);
        await this.password.fill(password);
        await this.loginButton.click();
    }

    private async flashText(): Promise<string>{
        await expect(this.flashMessage).toBeVisible();
        const txt = await this.flashMessage.textContent();
        return (txt ?? '').replace('x','').trim();
    }

    async assertLoginSuccess(){
        await expect(this.page).toHaveURL(/\/secure/);
        await expect(this.page.locator('#flash')).toContainText('You logged into a secure area!', {timeout: 5000});
    }    
    
    async assertLoginFailure(){
        await expect(this.page).toHaveURL(/\/login/);
        await expect(this.flashMessage).toContainText(/invalid!/i, {timeout:5000});
    }

    async logout(){
        await this.page.getByRole('link', {name: 'Logout'}).click();
        await expect(this.page).toHaveURL(/\/login/);
        await expect(this.flashMessage).toContainText('You logged out of the secure area!', {timeout: 5000});
    }
}