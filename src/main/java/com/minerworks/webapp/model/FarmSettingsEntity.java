package com.minerworks.webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "farm_settings")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "farmSettings")
@Data
public class FarmSettingsEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "farm_settings_id")
    private long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "farm_id")
    private FarmEntity farmEntity;

    @Column(name = "theme")
    private String theme;

    @Column(name = "language")
    private String language;

    @Column(name = "run_with_windows")
    private Boolean runWithWindows;

    @Column(name = "minimize_to_tray")
    private Boolean minimizeToTray;

    @Column(name = "hide_mining_window")
    private Boolean hideMiningWindow;

    @Column(name = "autostart_mining")
    private Boolean autostartMining;

    @Column(name = "idle_mining")
    private Boolean idleMining;

    @Column(name = "idle_mining_seconds")
    private Integer idleMiningSeconds;

    @Column(name = "currency_for_daily_revenue")
    private String currencyForDailyRevenue;

    @Column(name = "profitability_check_time_minutes")
    private Integer profitabilityCheckTimeMinutes;

    @Column(name = "charts_refresh_time_seconds")
    private Integer chartsRefreshTimeSeconds;

}
