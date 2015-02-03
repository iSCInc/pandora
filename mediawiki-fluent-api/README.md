# Mediawiki fluent api

Library created to use mediawiki api in fluent manner.

It's used decorator pattern and interfaces to filter choices, 

that you need to make while creating mediawiki api request

## Usage

MWApi.createBuilder(httpClient)
        .wikia("stargate")
        .queryAction()
        .titles("Aphofis")
        .prop().revisions()
        .rvlimit(1)
        .rvprop(RVPropEnum.content)
        .get();
        
Function get give you ApiResponse object, that contains structure of api response

Function url give you url created by builder

## expansion

Not all of Mediawiki api functionality is covered yet.

If you want to add property that you want to receive please look at MWApiRevisions

