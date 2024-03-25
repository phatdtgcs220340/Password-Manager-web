function getRequestParam(param) {
    const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        if (urlParams.has(param))
            return urlParams.get(param);
        else return null;

}