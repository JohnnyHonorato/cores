import {Component, EventEmitter, Input, OnInit, Output, QueryList, ViewChildren} from '@angular/core';
import {
    GridsterConfig,
    GridsterItem,
    GridsterItemComponent,
    GridsterItemComponentInterface
} from 'angular-gridster2';
import {configGrid} from './config-grid';

@Component({
    selector: 'tracker-custom-att-grid',
    templateUrl: './custom-att-grid.component.html',
    styleUrls: ['./custom-att-grid.component.scss']
})
export class CustomAttGridComponent implements OnInit {

    @ViewChildren(GridsterItemComponent) componentArray: QueryList<GridsterItemComponent> | any;

    @Input() receiveCustomAtt;

    @Output() updateItemList: EventEmitter<any> = new EventEmitter();

    options: GridsterConfig | any;

    grid: Array<GridsterItem> | any = [];

    constructor() {
    }

    ngOnInit() {
        this.options = configGrid;
        this.addGrid();
        this.options.itemChangeCallback = (item: GridsterItem, itemComponent: GridsterItemComponentInterface) => {
            this.updatePositions(this.grid);
            this.updateCoordinates(item);
            this.updateItemList.emit(this.grid);
        };
    }

    addGrid() {
        for (const att of this.receiveCustomAtt) {
            const newsAtt = {...att, ...this.getSizePositionOptions(att)};
            this.grid.push(newsAtt);
        }
    }

    updateCoordinates(attribute) {
        attribute.positionX = attribute.x;
        attribute.positionY = attribute.y;
        attribute.numberOfColumns = attribute.cols;
    }

    getSizePositionOptions(attribute) {
        return attribute.numberOfColumns ?
            {cols: attribute.numberOfColumns, rows: 1, y: attribute.positionY, x: attribute.positionX}
            : this.getPositionForNewAttribute();
    }

    updatePositions(grid) {
        grid.sort((a, b) => {
            if (a.y < b.y) {
                return -1;
            } else if (a.y > b.y) {
                return 1;
            } else {
                if (a.x < b.x) {
                    return -1;
                } else if (a.x > b.x) {
                    return 1;
                }
            }
        });
        for (let i = 0; i < grid.length; i++) {
            grid[i].position = i + 1;
        }
    }

    getPositionForNewAttribute() {
        const totalColumns = 12;
        const newAttributePositions = {cols: 3, rows: 1, y: 0, x: 0};
        if (this.grid.length > 0) {
            const lastElement = this.grid[this.grid.length - 1];
            const lastRow = lastElement.y;
            const lastColumnFree = lastElement.x + lastElement.cols;
            if (totalColumns - lastColumnFree >= newAttributePositions.cols) {
                newAttributePositions.y = lastRow;
                newAttributePositions.x = lastColumnFree;
            } else {
                newAttributePositions.y = lastRow + 1;
                newAttributePositions.x = 0;
            }
        }
        return newAttributePositions;
    }
}
