

export function authHeader(): Record<string, string> {
    const userStr = localStorage.getItem("user");

    if (userStr) {
        const user = JSON.parse(userStr) as { accessToken: string };

        if (user && user.accessToken) {
            return { Authorization: "Bearer" + user.accessToken };

        }

    }
    return {};

}
