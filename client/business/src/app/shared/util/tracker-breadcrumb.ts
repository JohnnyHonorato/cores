export const TRACKER_BREADCRUMBS = {
    tracker: {
        breadcrumbs: [
            {label: 'board.title.plural', url: 'tracker'}
        ],
        create: {
            breadcrumbs: [
                {label: 'board.title.plural', url: 'tracker'},
                {label: 'board.add', url: 'tracker/create'}
            ]
        },
        edit: {
            id: {
                breadcrumbs: [
                    {label: 'board.title.plural', url: 'tracker'},
                    {label: 'board.edit', url: 'tracker/edit/:id'}
                ]
            }
        },
        id: {
            breadcrumbs: [
                {label: 'board.title.plural', url: 'tracker'},
                {label: 'board.view', url: 'tracker/:id'}
            ],
            status: {
                id: {
                    card: {
                        create: {
                            breadcrumbs: [
                                {label: 'board.title.plural', url: 'tracker'},
                                {label: 'board.view', url: 'tracker/:id'},
                                {label: 'tracker.add', url: 'tracker/:id/status/:statusId/card/create'},
                            ]
                        },
                        view: {
                            id: {
                                breadcrumbs: [
                                    {label: 'board.title.plural', url: 'tracker'},
                                    {label: 'board.view', url: 'tracker/:id'},
                                    {label: 'tracker.view', url: 'tracker/:id/status/:statusId/card/view/:idCard'}
                                ]
                            }
                        },
                        edit: {
                            id: {
                                breadcrumbs: [
                                    {label: 'board.title.plural', url: 'tracker'},
                                    {label: 'board.view', url: 'tracker/:id'},
                                    {label: 'tracker.view', url: 'tracker/:id/status/:statusId/card/view/:idCard'},
                                    {label: 'tracker.edit', url: 'tracker/:id/status/:statusId/card/edit/:idCard'}
                                ]
                            }
                        }
                    }
                }
            }
        }
    }
};
