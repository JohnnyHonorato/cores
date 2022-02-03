import {Component, OnInit, ViewChild} from '@angular/core';
import {ModuleURL, RoleURL} from '@shared/util/url.domain';
import {BaseListComponent, DeleteConfirmationComponent} from 'common';

@Component({
    selector: 'core-role-list',
    templateUrl: './role.component.html',
    styleUrls: ['./role.component.scss']
})
export class RoleComponent extends BaseListComponent implements OnInit {

    @ViewChild(DeleteConfirmationComponent, {static: false})
    public deleteConfirmation: DeleteConfirmationComponent;

    public currentRole;
    public removeId: any;
    public filterRole: string;
    public filterPermission: string;

    public roles;
    public modules;

    openAccordion: boolean[] = [];

    constructor() {
        super();
    }

    ngOnInit() {
        super.ngOnInit();
        this.listModules();
    }

    getRouterURL(): string {
        return 'roles';
    }

    getServiceURL(): string {
        return RoleURL.BASE;
    }

    postInsert() {
        this.listRoles();
    }

    getFilteredRoles() {
        if (this.filterRole === undefined || this.filterRole.length === 0 || this.filterRole.trim() === '') {
            return this.roles;
        }

        return this.roles.filter((role) => {
            return role.name.toLowerCase().indexOf(this.filterRole.trim().toLowerCase()) >= 0;
        });
    }

    getFilteredModules() {
        if (this.filterPermission === undefined || this.filterPermission.length === 0 || this.filterPermission.trim() === '') {
            return this.modules;
        }

        return this.modules.filter((module) => {
            const index = this.modules.indexOf(module);
            this.openAccordion[index] = true;
            const permissions = module.permissions.filter(permission => {
                return permission.label.toLowerCase().indexOf(this.filterPermission.trim().toLowerCase()) >= 0;
            });
            return permissions.length > 0;
        });
    }

    getFilteredPermissions(module) {
        if (this.filterPermission === undefined || this.filterPermission.length === 0 || this.filterPermission.trim() === '') {
            return module.permissions;
        }

        return module.permissions.filter((permission) => {
            return permission.label.toLowerCase().indexOf(this.filterPermission.trim().toLowerCase()) >= 0;
        });
    }

    updatePermissions() {
        this.service.update(RoleURL.BASE, this.currentRole.id, this.currentRole)
            .subscribe(() => {
                this.notification.updateSuccess();
            }, (response) => {
                this.notification.error(response.error ? response.error.message : response.message);
            });
    }

    remove(id): void {
        this.removeId = id;
        if (!this.deleteConfirmation) {
            this.delete();
        } else {
            this.deleteConfirmation.subscribe(ok => {
                this.delete();
            });
        }
    }

    delete(): void {
        this.service.remove(this.getServiceURL(), this.removeId)
            .subscribe((result) => {
                this.notification.deleteSuccess();
                this.listRoles();
            }, (error) => {
                this.notification.error(error.error ? error.error.message : error.message);
            });
    }

    setCurrentRole(role) {
        if (this.currentRole && this.currentRole.id === role.id) {
            return;
        }
        this.currentRole = role;
        this.modules.forEach(module => {
            if (module.permissions) {
                module.permissions.forEach(permission => {
                    permission.checked = false;
                });
            }
        });

        if (this.currentRole.permissions && this.currentRole.permissions.length > 0) {
            this.currentRole.permissions.forEach((permission) => {
                this.modules.forEach((module) => {
                    if (module.permissions) {
                        module.permissions.forEach((modPermission) => {
                            if (permission.id === modPermission.id) {
                                modPermission.checked = true;
                            }
                        });
                    }
                });
            });
        }
    }

    addRemoveRolePermission(permission, isChecked) {
        const index = this.currentRole.permissions.findIndex(x => {
            return x.id === permission.id;
        });
        if (isChecked) {
            if (index < 0) {
                this.currentRole.permissions.push(permission);
            }
        } else if (index >= 0) {
            this.currentRole.permissions.splice(index, 1);
        }
    }

    backToList() {
        this.listRoles();
    }

    private listRoles() {
        this.service.get(`${this.getServiceURL()}/all`)
            .subscribe(result => {
                this.roles = result;
                if (this.roles.length) {
                    this.setCurrentRole(this.roles[0]);
                }
            }, (response) => {
                this.notification.error(response.error ? response.error.message : response.message);
            });
    }

    private listModules() {
        this.service.get(`${ModuleURL.BASE}/all`)
            .subscribe((result: any) => {
                this.modules = result;
                this.modules = this.modules.map((module) => {
                    module.expanded = false;
                    return module;
                });
                this.listRoles();
            }, (response) => {
                this.notification.error(response.error ? response.error.message : response.message);
            });
    }

}
